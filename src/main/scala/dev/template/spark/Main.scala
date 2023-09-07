package dev.template.spark

import dev.template.spark.source.Reader
import org.apache.spark.sql.functions.avg

import java.io.File

case class Person(firstName: String, lastName: String, country: String, age: Int)

class CalculateAverageAge extends SparkSessionWrapper with Reader with Logger {
  def calculateAverageAge(file: String): Double = {

    import spark.implicits._

    val version = spark.version
    log.info("SPARK VERSION = " + version)

    val sumHundred = spark.range(1, 101).as[Long].reduce(_ + _)
    log.info(f"Sum 1 to 100 = $sumHundred")

    log.info("Reading input file " + file)

    val persons = readCsv(spark).csv(file).as[Person]
    persons.show(2)
    val averageAge = persons.agg(avg("age")).first().get(0).asInstanceOf[Double]
    log.info(f"Average Age: $averageAge%.2f")
    averageAge
  }
}

object Main extends App {
  if (args.length == 0) {
    println(""" USAGE :
              | spark-submit \
              | --class dev.template.spark.Main \
              | --packages io.delta:delta-core_2.12:2.4.0 \
              | --master spark://localhost:7077 \
              | --deploy-mode client \
              | --driver-memory 1g \
              | --executor-memory 1g \
              | --executor-cores 2 \
              | build/libs/spark-scala-gradle-bootstrap-2.12.0-all.jar \
              | src/main/resources/people-example.csv
              |""".stripMargin)
    throw new RuntimeException("Requires input file people-example.csv")
  }
  private val inputFilePath = new File(args(0)).toString
  val calculateAverageAge = new CalculateAverageAge()
  calculateAverageAge.calculateAverageAge(inputFilePath)
}
