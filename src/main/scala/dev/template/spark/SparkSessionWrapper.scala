package dev.template.spark

import org.apache.spark.SparkContext
import org.apache.spark.sql.{SQLContext, SparkSession}

trait SparkSessionWrapper extends Logger with AutoCloseable {
  val spark: SparkSession = SparkSession
    .builder()
    .appName("Spark example")
    .config("option", "some-value")
    .enableHiveSupport()
    .config("spark.sql.parquet.enableVectorizedReader", "false")
    .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
    .config("spark.sql.catalog.spark_catalog",
            "org.apache.spark.sql.delta.catalog.DeltaCatalog")
    .getOrCreate()

  val sc: SparkContext = spark.sparkContext
  sc.setLogLevel("INFO")

  val sqlContext: SQLContext = spark.sqlContext

  override def close(): Unit = spark.close()
}
