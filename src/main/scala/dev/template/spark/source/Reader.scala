package dev.template.spark.source

import org.apache.spark.sql.SparkSession

trait Reader {

  def readCsv(spark: SparkSession) =
    spark
      .read
      .option("header", true)
      .option("inferSchema", true)
      .option("mode", "DROPMALFORMED")

  def readerCsvWithoutHeader(spark: SparkSession) = spark
    .read
    .option("header", false)
    .option("inferSchema", true)
    .option("mode", "DROPMALFORMED")

  def readKafka(spark: SparkSession, topic: String, options: Map[String, String]) = spark
    .read
    .format("kafka")
    .options(options)
    .option("subscribe", topic)
    .load()
}
