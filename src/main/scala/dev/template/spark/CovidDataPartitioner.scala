package dev.template.spark

import dev.template.spark.sink.Writer
import dev.template.spark.source.Reader
import org.apache.spark.sql.{Dataset, Row, SaveMode, SparkSession}

import java.io.File

object CovidDataPartitioner
    extends App
    with SparkSessionWrapper
    with Reader
    with Writer
    with Logger {
  def writeParquet(spark: SparkSession, file: String, outputPath: String): Unit = {
    val covidData: Dataset[Row] = readCsv(spark).csv(file).repartition(1)
    covidData.printSchema()
    covidData.createOrReplaceTempView("covid")

    val groupedView = sqlContext
      .sql("""
             | select
             |  cast(to_date(date, "yyyy-MM-dd") as String) as reported_date,
             |  county,
             |  state,
             |  fips,
             |  cases,
             |  deaths from covid
             |  group by all
             |
             |""".stripMargin)
      .cache()

    log.info(covidData.printSchema())

    writeParquet(groupedView, outputPath, SaveMode.Overwrite, Some("reported_date"))

  }

  if (args.length == 0) {
    println(""" USAGE :
              | spark-submit \
              | --class dev.template.spark.CovidDataPartitioner \
              | --packages io.delta:delta-core_2.12:2.4.0 \
              | --master spark://localhost:7077 \
              | --deploy-mode client \
              | --driver-memory 1g \
              | --executor-memory 1g \
              | --executor-cores 2 \
              | build/libs/spark-scala-gradle-bootstrap-2.12.0-all.jar \
              | src/main/resources/us-counties-recent.csv \
              | /tmp/partitioned-covid-data
              |""".stripMargin)
    throw new RuntimeException("Requires input file us-counties-recent.csv")
  }

  private val inputFilePath = new File(args(0)).toString
  private val outputPath = new File(args(1)).toString
  writeParquet(spark, inputFilePath, outputPath)

}
