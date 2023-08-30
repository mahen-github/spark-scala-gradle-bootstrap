package dev.template.spark

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.scalatest.funspec.AnyFunSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CovidDataPartitionerTest extends AnyFunSpec with SparkSessionTestWrapper {

  describe("should write data partitioned by reported date ") {
    val file = ClassLoader.getSystemResource("us-counties-recent.csv").getFile
    val outputPath = "/tmp/covid-data/us_covid_data/"
    CovidDataPartitioner.writeParquet(file, outputPath)
    it("returns 3257 rows for reported_date=2023-03-23") {
      def read = spark.read.parquet(outputPath + "reported_date=2023-03-23")

      assertEquals(3257, read.count())
    }

    it("number of partitions should be 25") {
      def read =
        spark.read.format("parquet").option("mergeSchema", "true").load(outputPath)

      read.createOrReplaceTempView("partitionedData")

      spark.sql("""drop table if exists covid_output""")
      val out = spark.sql("""create table covid_output partitioned by (reported_date)
                            |as select * from partitionedData;""".stripMargin)
      val part = spark.sql("show partitions covid_output")
      assertEquals(30, part.count())
    }
  }
}
