package com.example.urec

import java.util
import io.prediction.controller.P2LAlgorithm
import io.prediction.controller.Params
import io.prediction.data.storage.Event
import io.prediction.data.store.{PEventStore, LEventStore}
import org.apache.mahout.math.cf.SimilarityAnalysis
import org.apache.mahout.sparkbindings.indexeddataset.IndexedDatasetSpark
import scala.collection.JavaConverters._
import scala.concurrent.duration.Duration
import org.apache.spark.SparkContext
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import scala.collection.convert.wrapAsScala._
import grizzled.slf4j.Logger

/** Setting the option in the params case class doesn't work as expected when the param is missing from
  * engine.json so set these for use in the algorithm when they are not present in the engine.json
  */
object defaultURAlgorithmParams {
  val DefaultMaxEventsPerEventType = 500
  val DefaultNum = 20
  val DefaultMaxCorrelatorsPerEventType = 50
  val DefaultMaxQueryEvents = 100 // default number of user history events to use in recs query
}

/** Instantiated from engine.json */
case class URAlgorithmParams(
  appName: String, // filled in from engine.json
  indexName: String, // can optionally be used to specify the elasticsearch index name
  typeName: String, // can optionally be used to specify the elasticsearch type name
  eventNames: List[String], // names used to ID all user actions
  // list of events used to determine which recs to filter out, used for
  // things like not showing items a user has purchased. Default is anything
  // the user took the primary action on, to filter nothing specify an
  // empty array in engine.json
  blacklistEvents: Option[List[String]] = None,
  //todo: backfill: Option[String] = None, // popular or trending
  // number of events in user-based recs query
  maxQueryEvents: Option[Int] = Some(defaultURAlgorithmParams.DefaultMaxQueryEvents),
  maxEventsPerEventType: Option[Int] = Some(defaultURAlgorithmParams.DefaultMaxEventsPerEventType),
  maxCorrelatorsPerEventType: Option[Int] = Some(defaultURAlgorithmParams.DefaultMaxCorrelatorsPerEventType),
  num: Option[Int] = Some(defaultURAlgorithmParams.DefaultNum), // default max # of recs requested
  userBias: Option[Float] = None, // will cause the default search engine boost of 1.0
  itemBias: Option[Float] = None, // will cause the default search engine boost of 1.0
  returnSelf: Option[Boolean] = None, // query building logic defaults this to false
  fields: Option[List[Field]] = None, //defaults to no fields
  seed: Option[Long] = None) // seed is not used presently
  extends Params //fixed default make it reproducable unless supplied

/** Creates cooccurrence, cross-cooccurrence and eventually content correlators with
  * [[org.apache.mahout.math.cf.SimilarityAnalysis]] The analysis part of the recommender is
  * done here but the algorithm can predict only when the coocurrence data is indexed in a
  * search engine like Elasticsearch. This is done in URModel.save.
  *
  * @param ap taken from engine.json to describe limits and event types
  */
class URAlgorithm(val ap: URAlgorithmParams)
  extends P2LAlgorithm[PreparedData, URModel, Query, PredictedResult] {

  case class BoostableCorrelators(actionName: String, itemIDs: Seq[String], boost: Option[Float])
  case class FilterCorrelators(actionName: String, itemIDs: Seq[String])

  @transient lazy val logger = Logger[this.type]

  def train(sc: SparkContext, data: PreparedData): URModel = {
    // No one likes empty training data.
    require(data.actions.take(1).nonEmpty,
      s"Primary action in PreparedData cannot be empty." +
        " Please check if DataSource generates TrainingData" +
        " and Preprator generates PreparedData correctly.")

    logger.info("Actions read now creating correlators")
    val cooccurrenceIDSs = SimilarityAnalysis.cooccurrencesIDSs(
      data.actions.map(_._2).toArray,
      randomSeed = ap.seed.getOrElse(System.currentTimeMillis()).toInt,
      maxInterestingItemsPerThing = ap.maxCorrelatorsPerEventType
        .getOrElse(defaultURAlgorithmParams.DefaultMaxCorrelatorsPerEventType),
      maxNumInteractions = ap.maxEventsPerEventType.getOrElse(defaultURAlgorithmParams.DefaultMaxEventsPerEventType))
      .map(_.asInstanceOf[IndexedDatasetSpark]) // strip action names
    val cooccurrenceCorrelators = cooccurrenceIDSs.zip(data.actions.map(_._1)).map(_.swap) //add back the actionNames

    logger.info("Correlators created now putting into URModel")
    new URModel(cooccurrenceCorrelators, data.fieldsRDD, ap.indexName)
  }

  /** Return a list of items recommended for a user identified in the query
    * The ES json query looks like this:
    *  {
    *    "size": 20
    *    "query": {
    *      "bool": {
    *        "should": [
    *          {
    *            "terms": {
    *              "rate": ["0", "67", "4"]
    *            }
    *          },
    *          {
    *            "terms": {
    *              "buy": ["0", "32"],
    *              "boost": 2
    *            }
    *          }
    *        ],
    *        "must": [
    *          {
    *            "terms": {
    *              "category": ["cat1"]
    *            }
    *          }
    *        ]
    *      }
    *    }
    *  }
    *
    * @param model <strong>Ignored!</strong> since the model is already in Elasticsearch
    * @param query contains query spec
    */
  def predict(model: URModel, query: Query): PredictedResult = {
    logger.info(s"Query received, user id: ${query.user}, item id: ${query.item}")

    val queryAndBlacklist = buildQuery(ap, query)
    val recs = esClient.search(queryAndBlacklist._1, ap.indexName)
    removeBlacklisted(recs, query, queryAndBlacklist._2)
  }

  /** take out any recs that have been blacklisted, perhaps because the user has seen them before or
    * because they are already shown on an app screen/page.
    * @param recs recs to be filtered
    * @param query query for
    * @param userActions
    * @return
    */
  def removeBlacklisted(recs: PredictedResult, query: Query, userActions: List[Event] ): PredictedResult = {

    // First remove any events that have targets not allowed
    val disallowedEvents = userActions.filter { event =>
      if (ap.blacklistEvents.nonEmpty) {
        // either a list or an empty list of filtering events so honor them
        if (ap.blacklistEvents.get == List.empty[String]) false // no filtering events so all are allowed
        else if (ap.blacklistEvents.get.contains(event.event)) true else false // if its filtered remove it, else allow
      } else if (ap.eventNames(0).equals(event.event)) true else false // remove the primary event if nothing specified
    }

    val allowedItemScores = recs.itemScores.filter { itemScore =>
      !disallowedEvents.exists ( event => itemScore.item.equals(event.targetEntityId.getOrElse("")))
    }

    // Now remove any items disallowed in query
    val queryFilteredItemScores = allowedItemScores.filterNot { itemScore =>
      query.blacklistItems.getOrElse(List.empty[String]).contains(itemScore.item)
    } //if some rec id == something blacklisted in query then don't return the itemScore

    // Now conditionally filter the query item itself
    val includeSelf = query.returnSelf.getOrElse(ap.returnSelf.getOrElse(false))
    val selfFilteredItemScores = if ( !includeSelf ) queryFilteredItemScores.filterNot { itemScore =>
      query.item.getOrElse("").equals(itemScore.item)
    } else queryFilteredItemScores //don't filter out the query item
    PredictedResult(selfFilteredItemScores)
  }

  /** Build a query from default algorithms params and the query itself taking into account defaults */
  def buildQuery(ap: URAlgorithmParams, query: Query): (String, List[Event]) = {

    try{ // require the minimum of a user or item, if not then return nothing
      require( query.item.nonEmpty || query.user.nonEmpty, "Warning: a query must include either a user or item id")

      // create a list of all query correlators that can have a bias (boost or filter) attached
      val alluserEvents = getBiasedRecentUserActions(query)

      // create a list of all boosted query correlators
      val recentUserHistory = if ( ap.userBias.getOrElse(1f) >= 0f )
        alluserEvents._1.slice(0, ap.maxQueryEvents.getOrElse(defaultURAlgorithmParams.DefaultMaxQueryEvents) - 1)
      else List.empty[BoostableCorrelators]

      val similarItems = if ( ap.itemBias.getOrElse(1f) >= 0f )
        getBiasedSimilarItems(query)
      else List.empty[BoostableCorrelators]

      val boostedMetadata = getBoostedMetadata(query)

      val allBoostedCorrelators = recentUserHistory ++ similarItems ++ boostedMetadata

      // create a lsit of all query correlators that are to be used to filter results
      val recentUserHistoryFilter = if ( ap.userBias.getOrElse(1f) < 0f ) {
        // strip any boosts
        alluserEvents._1.map { i =>
          FilterCorrelators(i.actionName, i.itemIDs)
        }.slice(0, ap.maxQueryEvents.getOrElse(defaultURAlgorithmParams.DefaultMaxQueryEvents) - 1)
      } else List.empty[FilterCorrelators]

      val similarItemsFilter = if ( ap.itemBias.getOrElse(1f) < 0f ) {
        getBiasedSimilarItems(query).map { i =>
          FilterCorrelators(i.actionName, i.itemIDs)
        }.toList
      } else List.empty[FilterCorrelators]

      val filteringMetadata = getFilteringMetadata(query)

      val allFilteringCorrelators = recentUserHistoryFilter ++ similarItemsFilter ++ filteringMetadata

      // since users have action history and items have correlators and both correspond to the same "actions" like
      // purchase or view, we'll pass both to the query if the user history or items correlators are empty
      // then metadata or backfill must be relied on to return results.

      val numRecs = query.num.getOrElse(ap.num.getOrElse(defaultURAlgorithmParams.DefaultNum))

      val json =
        (
          ("size" -> numRecs) ~
            ("query"->
              ("bool"->
                ("should"->
                  allBoostedCorrelators.map { i =>
                    ("terms" -> (i.actionName -> i.itemIDs) ~ ("boost" -> i.boost))}) ~
                ("must"->
                  allFilteringCorrelators.map { i =>
                    ("terms" -> (i.actionName -> i.itemIDs))}))))
       val j = compact(render(json))
      // logger.info(s"Query: \n${j}\n")
      (compact(render(json)), alluserEvents._2)
    } catch {
      case e: IllegalArgumentException =>
        ("", List.empty[Event])
    }
  }

  /** Get similar items for an item, these are already in the action correlators in ES */
  def getBiasedSimilarItems(query: Query): Seq[BoostableCorrelators] = {
    if (query.item.nonEmpty) {
      val m = esClient.getSource(ap.indexName, ap.typeName, query.item.get)

      if (m != null) {
        val itemEventBias = query.itemBias.getOrElse(ap.itemBias.getOrElse(1f))
        val itemEventsBoost = if (itemEventBias > 0 && itemEventBias != 1) Some(itemEventBias) else None
        ap.eventNames.map { action =>
          val items = try {
            if (m.containsKey(action) && m.get(action) != null) m.get(action).asInstanceOf[util.ArrayList[String]].toList
            else List.empty[String]
          } catch {
            case cce: ClassCastException =>
              logger.warn(s"Bad value in item ${query.item} corresponding to key: ${action} that was not a List[String]" +
                " ignored.")
              List.empty[String]
          }
          val rItems = if (items.size <= ap.maxQueryEvents.getOrElse(defaultURAlgorithmParams.DefaultMaxQueryEvents))
            items else items.slice(0, ap.maxQueryEvents.getOrElse(defaultURAlgorithmParams.DefaultMaxQueryEvents) - 1)
          BoostableCorrelators(action, rItems, itemEventsBoost)
        }
      } else List.empty[BoostableCorrelators] // no similar items
    } else List.empty[BoostableCorrelators] // no item specified
  }

  /** Get recent events of the user on items to create the recommendations query from */
  def getBiasedRecentUserActions(
    query: Query): (Seq[BoostableCorrelators], List[Event]) = {

    val recentEvents = try {
      /* to retur an rdd use
      PEventStore.find(
        appName = ap.appName,
        entityType = Some("user"),
        entityId = Some(query.user.get),
        eventNames = Some(ap.eventNames)
      )(sc).collect().toList
      */
      LEventStore.findByEntity(
        appName = ap.appName,
        // entityType and entityId is specified for fast lookup
        entityType = "user",
        entityId = query.user.get,
        // one query per eventName is not ideal, maybe one query for lots of events then split by eventName
        //eventNames = Some(Seq(action)),// get all and separate later
        targetEntityType = None,
        // limit = Some(maxQueryEvents), // this will get all history then each action can be limited before using in
        // the query
        latest = true,
        // set time limit to avoid super long DB access
        timeout = Duration(200, "millis")
      ).toList
    } catch {
      case e: scala.concurrent.TimeoutException =>
        logger.error(s"Timeout when read recent events." +
          s" Empty list is used. ${e}")
        List.empty[Event]
      case e: NoSuchElementException => // todo: bad form to use an exception to check if there is a user id
        logger.info("No user id for recs, returning similar items for the item specified")
        List.empty[Event]
      case e: Exception => // fatal because of error, an empty query
        logger.error(s"Error when read recent events: ${e}")
        throw e
    }

    val userEventBias = query.userBias.getOrElse(ap.userBias.getOrElse(1f))
    val userEventsBoost = if (userEventBias > 0 && userEventBias != 1) Some(userEventBias) else None
    val rActions = ap.eventNames.map { action =>
      var items = List[String]()

      for ( event <- recentEvents )
        if (event.event == action && items.size <
          ap.maxQueryEvents.getOrElse(defaultURAlgorithmParams.DefaultMaxQueryEvents)) {
          items = event.targetEntityId.get :: items
          // todo: may throw exception and we should ignore the event instead of crashing
        }
      // userBias may be None, which will cause no JSON output for this
      BoostableCorrelators(action, items.distinct, userEventsBoost)
    }
    (rActions, recentEvents)
  }

  /** get all metadata fields that potentially have boosts (not filters) */
  def getBoostedMetadata( query: Query ): List[BoostableCorrelators] = {
    val paramsBoostedFields = ap.fields.getOrElse(List.empty[Field]).filter( field => field.bias < 0 ).map { field =>
      BoostableCorrelators(field.name, field.values, Some(field.bias))
    }

    val queryBoostedFields = query.fields.getOrElse(List.empty[Field]).filter { field =>
      field.bias >=  0f
    }.map { field =>
      BoostableCorrelators(field.name, field.values, Some(field.bias))
    }

    (queryBoostedFields ++ paramsBoostedFields).distinct  // de-dup and favor query fields
  }

  /** get all metadata fields that are filters (not boosts) */
  def getFilteringMetadata( query: Query ): List[FilterCorrelators] = {
    val paramsFilterFields = ap.fields.getOrElse(List.empty[Field]).filter( field => field.bias >= 0 ).map { field =>
      FilterCorrelators(field.name, field.values)
    }

    val queryFilterFields = query.fields.getOrElse(List.empty[Field]).filter { field =>
      field.bias <  0f
    }.map { field =>
      FilterCorrelators(field.name, field.values)
    }

    (queryFilterFields ++ paramsFilterFields).distinct // de-dup and favor query fields
  }

}