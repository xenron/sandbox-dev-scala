package com.packt.dataload

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.rdd.RDD
import scala.util.Try
import FlattenUtils._
import com.packt.dataload.model.Student


object DataFrameFromProductClass extends App {

  val conf = new SparkConf().setAppName("DataWith33Atts").setMaster("local[2]")

  val sc = new SparkContext(conf)

  val sqlContext = new SQLContext(sc)

  val rddOfStudents = convertCSVToStudents("student-mat.csv", sc)

  import sqlContext.implicits._
  
  //Create DataFrame
  val studentDFrame = rddOfStudents.toDF()

  studentDFrame.printSchema()

  studentDFrame.show()

  def convertCSVToStudents(filePath: String, sc: SparkContext): RDD[Student] = {
    val rddOfOptionStudents: RDD[Option[Student]] = sc.textFile(filePath).map(eachLine => Student(eachLine))
    rddOfOptionStudents.flatten
  }

}