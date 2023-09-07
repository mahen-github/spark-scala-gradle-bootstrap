package dev.template.spark

import org.apache.log4j.Level
import org.apache.spark.SparkContext
import org.apache.spark.sql.{SQLContext, SparkSession}

trait SparkSessionWrapper extends Logger with AutoCloseable {
  lazy val spark: SparkSession = SparkSession
    .builder()
    .appName("Spark example")
    .enableHiveSupport()
    .config("spark.sql.warehouse.dir", "file:///tmp/spark-warehouse")
    .config("spark.sql.parquet.enableVectorizedReader", "false")
    .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
    .config("spark.sql.catalog.spark_catalog",
            "org.apache.spark.sql.delta.catalog.DeltaCatalog")
    .getOrCreate()

  val sc: SparkContext = spark.sparkContext
  sc.setLogLevel(Level.INFO.toString)
  val sqlContext: SQLContext = spark.sqlContext

  override def close(): Unit = spark.close()
}
