package dev.template.spark

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.scalatest.funspec.AnyFunSpec
import org.scalatestplus.junit.JUnitRunner

import java.net.URL

@RunWith(classOf[JUnitRunner])
class MainTest extends AnyFunSpec {

  describe("MainTest") {
    it("calculates average age of the person data") {
      val file: URL =
        ClassLoader.getSystemClassLoader.getResource("people-example.csv")
      assertEquals("23.0".toDouble, Main.calculateAverageAge(file.getFile).floor)
    }
  }
}
