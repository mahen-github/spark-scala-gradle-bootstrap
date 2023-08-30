package dev.template.spark

import org.apache.spark.sql.SparkSession

trait SparkSessionTestWrapper {

  val spark = SparkSession
    .builder
    .master("local[*]")
    .config("spark.hadoop.hive.exec.dynamic.partition.mode", "nostrict")
    .config("hive.exec.dynamic.partition", "true")
    .enableHiveSupport()
    .getOrCreate()
}
