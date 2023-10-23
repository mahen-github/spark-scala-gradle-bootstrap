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

  def readDelta(spark: SparkSession, path: String, options: Map[String, String] = Map()) =
    spark.read.format("delta").options(options).load(path)

  /**
   * Kafka reader requires kafka consumer properties.
   *
   * @param spark
   *   spark session
   * @param topic
   *   kafka topic to consume
   * @param kafkaConfig
   *   Kafka consumer properties
   * @return
   */
  def readKafka(spark: SparkSession, topic: String, kafkaConfig: Map[String, String] = Map()) =
    spark
      .read
      .format("kafka")
      .options(kafkaConfig)
      .option("subscribe", topic)
      .option("startingOffsets", "earliest")
      .option("endingOffsets", "latest")
      .load()

}
