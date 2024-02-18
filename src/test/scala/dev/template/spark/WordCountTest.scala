package dev.template.spark

import org.junit.runner.RunWith
import org.scalatest.funspec.AnyFunSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class WordCountTest extends AnyFunSpec with SparkSessionTestWrapper {

  describe("WordCountTest") {
    it("should executeWordCount") {

      val input = ClassLoader.getSystemResource("wordcount_intput.txt").getFile
      val output = ClassLoader.getSystemResource("").getFile + "/wordcount_output"

      println(output)
      println(input)
      WordCount
        .executeWordCount(sparkContext = spark.sparkContext, Array(input, output))

      val lines = spark.sparkContext.textFile(output + "/part-*")
      assert(lines.count() == 5)
    }
  }
}
