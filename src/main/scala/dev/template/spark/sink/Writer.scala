package dev.template.spark.sink

import org.apache.spark.sql.{DataFrame, SaveMode}

trait Writer {
  def writeParquet(df: DataFrame,
                   path: String,
                   mode: SaveMode = SaveMode.Overwrite,
                   partition: Option[String]): Unit = {
    val dfWriter = df
      .write
      .mode(mode)
    if (partition.isDefined) {
      dfWriter.partitionBy(partition.get)
    }
    dfWriter.save(path)
  }

  def writeCsv(df: DataFrame,
               path: String,
               mode: SaveMode = SaveMode.Overwrite,
               options: Map[String, String],
               partition: Option[String]): Unit = {
    val dfWriter = df
      .write
      .options(options)
      .mode(mode)
    if (partition.isDefined) {
      dfWriter.partitionBy(partition.get)
    }
    dfWriter.csv(path)
  }

}
