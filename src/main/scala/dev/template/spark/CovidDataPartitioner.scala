package dev.template.spark

import dev.template.spark.sink.Writer
import dev.template.spark.source.Reader
import org.apache.spark.sql.{Dataset, Row, SaveMode}

object CovidDataPartitioner
    extends App
    with SparkSessionWrapper
    with Reader
    with Writer
    with Logger {
  def writeParquet(file: String, outputPath: String): Unit = {
    val covidData: Dataset[Row] = readCsv(spark).csv(file).repartition(1)
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

}
