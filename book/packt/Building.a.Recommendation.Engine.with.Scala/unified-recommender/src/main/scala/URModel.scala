package com.example.urec

import grizzled.slf4j.Logger

import io.prediction.controller.{PersistentModelLoader, PersistentModel}
import io.prediction.data.storage.PropertyMap
import org.apache.spark.rdd.RDD
import org.apache.mahout.sparkbindings.indexeddataset.IndexedDatasetSpark
import com.example.urec.conversions.IndexedDatasetConversions
import org.elasticsearch.spark._
import org.apache.spark.SparkContext


/** Universal Recommender models to save in ES */
class URModel(
  coocurrenceMatrices: List[(String, IndexedDatasetSpark)],
  fieldsRDD: RDD[(String, PropertyMap)],
  indexName: String,
  nullModel: Boolean = false)
// a little hack to allow a dummy model used to save but not
// retrieve (see companion object's apply)
  extends PersistentModel[URAlgorithmParams] {
  @transient lazy val logger = Logger[this.type]

  /** Save all fields to be indexed by Elasticsearch and queried for recs
    * This will is something like a table with row IDs = item IDs and separate fields for all
    * cooccurrence and cross-cooccurrence correlators and metadata for each item. Metadata fields are
    * limited to text term collections so vector types. Scalar values can be used but depend on
    * Elasticsearch's support. One exception is the Data scalar, which is also supported
    * @param id
    * @param params from engine.json, algorithm control params
    * @param sc The spark constext already created for execution
    * @return always returns true since most other reasons to not save cause exceptions
    */
  def save(id: String, params: URAlgorithmParams, sc: SparkContext): Boolean = {

    if (nullModel) throw new IllegalStateException("Saving a null model created from loading an old one.")

    // for ES we need to create the entire index in an rdd of maps, one per item so we'll use
    // convert cooccurrence matrices into correlators as RDD[(itemID, (actionName, Seq[itemID])]
    // do they need to be in Elasticsearch format
    logger.info("Converting cooccurrence matrices into correlators")
    val correlators = coocurrenceMatrices.map { case (actionName, dataset) =>
      val db = dataset.matrix.nrow
      val db2 = dataset.matrix.ncol
      dataset.toStringMapRDD(actionName)
    }

    val allActions = coocurrenceMatrices.map(_._1)

    // convert the PropertyMap into Map[String, Seq[String]] for ES
    logger.info("Converting PropertyMap into Elasticsearch style rdd")
    val properties = fieldsRDD.map { case (item, pm ) =>
      var m: Map[String, Seq[String]] = Map()
      for (key <- pm.keySet){
        m = m  + (key -> pm.get[List[String]](key))
      }
      (item, m)
    }

    val allPropKeys = properties.flatMap(_._2.keySet).distinct.collect()

    // these need to be indexed with "not_analyzed" and no norms so have to
    // collect all field names before ES index create
    val allFields = (allActions ++ allPropKeys).distinct // shouldn't need distinct but it's fast

    // Elasticsearch takes a Map with all fields, not a tuple
    logger.info("Grouping all correlators into doc + fields for writing to index")
    val esFields = groupAll((correlators :+ properties).filterNot(c => c.isEmpty())).map { case (item, map) =>
      val esMap = map + ("id" -> item)
      esMap
    }

    val esIndexURI = s"/${params.indexName}/${params.typeName}"
    logger.info(s"Deleting index: ${esIndexURI}")
    esClient.deleteIndex(params.indexName)
    logger.info(s"Creating new index: ${esIndexURI}")
    esClient.createIndex(params.indexName, params.typeName, allFields)

    // es.mapping.id needed to get ES's doc id out of each rdd's Map("id")
    logger.info(s"Writing new ES style rdd to index: ${esIndexURI}")
    esFields.saveToEs (esIndexURI, Map("es.mapping.id" -> "id"))
    // todo: check to see if a flush or refresh is needed after writing all new data to the index
    // esClient.admin().indices().flush(new FlushRequest(params.indexName)).actionGet()
    logger.info(s"Finished writing to index: /${params.indexName}/${params.typeName}")
    true
  }

  def groupAll( fields: Seq[RDD[(String, (Map[String, Seq[String]]))]]): RDD[(String, (Map[String, Seq[String]]))] = {
    if (fields.size > 1 && !fields.head.isEmpty() && !fields(1).isEmpty()) {
      fields.head.cogroup[Map[String, Seq[String]]](groupAll(fields.drop(1))).map { case (key, pairMapSeqs) =>
        // to be safe merge all maps but should only be one per rdd element
        val rdd1Maps = pairMapSeqs._1.foldLeft(Map.empty[String, Seq[String]])(_ ++ _)
        val rdd2Maps = pairMapSeqs._2.foldLeft(Map.empty[String, Seq[String]])(_ ++ _)
        val fullMap = rdd1Maps ++ rdd2Maps
        /*        if (pairMapSeqs._1.nonEmpty && pairMapSeqs._2.nonEmpty)
                  (key, pairMapSeqs._1.head ++ pairMapSeqs._2.head)
                else if (pairMapSeqs._1.isEmpty && pairMapSeqs._2.nonEmpty)
                  (key, pairMapSeqs._2.head)// only ever one map per list since they were from dictinct rdds
                else if (pairMapSeqs._1.nonEmpty && pairMapSeqs._2.isEmpty)
                  (key, pairMapSeqs._1.head)// only ever one map per list since they were from dictinct rdds
                else
                  (key, Map.empty[String, Seq[String]])// yikes, this should never happen but ok, check
        */
        (key, fullMap)
      }
    } else fields.head
  }

  override def toString = {
    /*s"userFeatures: [${userFeatures.count()}]" +
      s"(${userFeatures.take(2).toList}...)" +
      s" productFeatures: [${productFeatures.count()}]" +
      s"(${productFeatures.take(2).toList}...)" +
      s" userStringIntMap: [${userStringIntMap.size}]" +
      s"(${userStringIntMap.take(2)}...)" +
      s" itemStringIntMap: [${itemStringIntMap.size}]" +
      s"(${itemStringIntMap.take(2)}...)" */
    s"URModel in Elasticsearch at index: ${indexName}"
  }


}

object URModel
  extends PersistentModelLoader[URAlgorithmParams, URModel] {
  @transient lazy val logger = Logger[this.type]

  /** This is actually only used to read saved values and since they are in Elasticsearch we don't need to read
    * this means we create a null model since it will not be used.
    * todo: we should rejigger the template framework so this is not required.
    * @param id ignored
    * @param params ignored
    * @param sc ignored
    * @return dummy null model
    */
  def apply(id: String, params: URAlgorithmParams, sc: Option[SparkContext]): URModel = {
    // todo: need changes in PIO to remove the need for this
    val urm = new URModel(null, null, null, true)
    logger.info("Created dummy null model")
    urm
  }

}