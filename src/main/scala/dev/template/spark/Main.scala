package dev.template.spark

import dev.template.spark.source.Reader
import org.apache.spark.sql.functions.avg

case class Person(firstName: String, lastName: String, country: String, age: Int)

object Main extends SparkSessionWrapper with Reader with Logger {

  def calculateAverageAge(file: String): Double = {

    import spark.implicits._

    val version = spark.version
    log.info("SPARK VERSION = " + version)

    val sumHundred = spark.range(1, 101).as[Long].reduce(_ + _)
    log.info(f"Sum 1 to 100 = $sumHundred")

    log.info("Reading from csv file: people-example.csv")

    val persons = readCsv(spark).csv(file).as[Person]
    persons.show(2)
    val averageAge = persons.agg(avg("age")).first().get(0).asInstanceOf[Double]
    log.info(f"Average Age: $averageAge%.2f")
    averageAge
  }
}
