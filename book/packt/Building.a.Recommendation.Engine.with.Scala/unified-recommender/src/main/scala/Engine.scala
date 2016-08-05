package com.example.urec

import io.prediction.controller.{EngineFactory, Engine}

/** This file contains case classes that are used with reflection to specify how query and config
  * JSON is to be parsed. the Query case class, for instance defines the way a JSON query is to be
  * formed. The same for param case classes.
  */

/** The Query spec with optional values. The only hard rule is that there must be either a user or
  * an item id. All other values are optional. */
case class Query(
    user: Option[String] = None, // must be a user or item id
    userBias: Option[Float] = None, // default: whatever is in algorithm params or 1
    item: Option[String] = None, // must be a user or item id
    itemBias: Option[Float] = None, // default: whatever is in algorithm params or 1
    fields: Option[List[Field]] = None, // default: whatever is in algorithm params or None
    blacklistItems: Option[List[String]] = None, // default: whatever is in algorithm params or None
    returnSelf: Option[Boolean] = None,// means for an item query should the item itself be returned, defaults
                                       // to what is in the algorithm params or false
    num: Option[Int] = None) // default: whatever is in algorithm params, which itself has a default--probably 20
  extends Serializable

/** Used to specify how Fields are represented in engine.json */
case class Field( // no optional values for fields, whne specified
    name: String, // name of metadata field
    values: Array[String], // fields can have multiple values like tags of a single value as when using hierarchical
                           // taxonomies
    bias: Float) // any positive value is a boost, negative is a filter
  extends Serializable

/** results of a MMRAlgoritm.predict */
case class PredictedResult(
    itemScores: Array[ItemScore])
  extends Serializable

case class ItemScore(
    item: String, // item id
    score: Double )// used to rank, original score returned from teh search engine
  extends Serializable

object RecommendationEngine extends EngineFactory {
  def apply() = {
    new Engine(
      classOf[DataSource],
      classOf[Preparator],
      Map("ur" -> classOf[URAlgorithm]), // IMPORTANT: "mmr" must be the "name" of the parameter set in engine.json
      classOf[Serving])
  }
}