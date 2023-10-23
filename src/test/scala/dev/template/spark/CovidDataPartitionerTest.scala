package dev.template.spark

import org.apache.hadoop.fs.{FileSystem, Path, PathFilter}
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.scalatest.funspec.AnyFunSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CovidDataPartitionerTest extends AnyFunSpec with SparkSessionTestWrapper {

  describe("should write data partitioned by reported date ") {
    val file = ClassLoader.getSystemResource("us-counties-recent.csv").getFile
    val outputPath = "/tmp/covid-data/us_covid_data/"
    CovidDataPartitioner.writeParquet(spark, file, outputPath)
    it("returns 3257 rows for reported_date=2023-03-23") {
      def read = spark.read.parquet(outputPath + "reported_date=2023-03-23")

      assertEquals(3257, read.count())
    }

    it("number of reported_date partitions should be 30") {

      val fs = FileSystem.get(spark.sparkContext.hadoopConfiguration)
      val reportedDateFolderCount = fs
        .listStatus(new Path(outputPath),
                    new PathFilter {
                      override def accept(path: Path): Boolean =
                        path.getName.contains("reported_date")
                    })
        .length

      assertEquals(30, reportedDateFolderCount)

      /**
       * the below CTAS fails when running in java 8 with the error message. Leaving it here for
       * reference and replacing the test to get count with FileSystem API
       *
       * A fatal error has been detected by the Java Runtime Environment: # # SIGSEGV (0xb) at
       * pc=0x000000010c0d604e, pid=65727, tid=0x0000000000002703
       */

      //      def read =
      //        spark.read.format("parquet").option("mergeSchema", "true").load(outputPath)
      //      read.createOrReplaceTempView("partitionedData")
      //
      //      spark.sql("""drop table if exists covid_output""")
      //      val out = spark.sql("""create table  covid_output partitioned by (reported_date)
      //                            |as select * from partitionedData;""".stripMargin)
      //      val part = spark.sql("show partitions covid_output")
      //      assertEquals(30, part.count())
    }
  }
}
