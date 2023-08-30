package dev.template.spark

import org.apache.log4j

trait Logger {
  @transient lazy val log: log4j.Logger =
    org.apache.log4j.LogManager.getLogger(getClass.getName)
}
