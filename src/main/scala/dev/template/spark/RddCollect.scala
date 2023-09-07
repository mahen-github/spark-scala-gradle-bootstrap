package dev.template.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object RddCollect {

  /**
   * A simple spark application with SparkSession in local mode and creates an RDD, collect and
   * print the results.
   *
   * @param args
   *   program arguments
   */
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("Spark RDD example")
      .master("local[2]")
      .getOrCreate()
    val rdd: RDD[Int] = spark.sparkContext.parallelize(List(1, 2, 3, 4, 5))
    val rddCollect: Array[Int] = rdd.collect()
    println("Number of Partitions: " + rdd.getNumPartitions)
    println("Action: First element: " + rdd.first())
    println("Action: RDD converted to Array[Int] : ")
    rddCollect.foreach(println)
  }
}
