# _spark-scala-gradle-bootstrap_

A Spark bootstrap project written in Scala with gradle as build tool.

## Prerequisites

- [Java 8](https://docs.aws.amazon.com/corretto/latest/corretto-8-ug/macos-install.html)
- [Scala 2.12](https://www.scala-lang.org/download/2.12.0.html)
- [spark 3.4.1](https://spark.apache.org/downloads.html)

## Libraries Included

- JavaVersion=1.11
- sparkVersion=3.4.1
- scalaVersion=2.12
- deltaVersion=2.4.0

### Build

`java -version`

    openjdk version "11.0.20" 2023-07-18
    OpenJDK Runtime Environment Homebrew (build 11.0.20+0)
    OpenJDK 64-Bit Server VM Homebrew (build 11.0.20+0, mixed mode)

`./gradlew clean build`

### Test

	./gradlew check

### Run TestCoverage

	./gradlew reportTestScoverage

## Run

#### Run sparkSubmit task

Gradle sparkSubmit task is configured to run with class the `dev.template.spark.RddCollect`

	./gradlew sparkSubmit

#### Spark Submit commands in shell

> A local spark instance must be up and running
> - spark web ui http://localhost:8080/
> - spark history server http://localhost:18080/

##### Run the Main class reads people-example.csv and get the average age

	spark-3.4.1-bin-hadoop3/bin/spark-submit \
		--verbose \
		--class dev.template.spark.Main \
		--packages io.delta:delta-core_2.12:2.4.0 \
		--master spark://localhost:7077 \
		--driver-memory 1g \
		--executor-memory 1g \
		--executor-cores 2 \
		build/libs/spark-scala-gradle-bootstrap-2.12.0-all.jar \
		src/main/resources/people-example.csv \

##### Run a simple app RddCollect with spark session in local

	spark-3.4.1-bin-hadoop3/bin/spark-submit \
		--class dev.template.spark.RddCollect \
		--master spark://localhost:7077 \
		build/libs/spark-scala-gradle-bootstrap-2.12.0-all.jar

##### Run CovidDataPartitioner app reads covid deaths in US counties and partitioned by reported date

	spark-3.4.1-bin-hadoop3/bin/spark-submit --class dev.template.spark.CovidDataPartitioner \
		--packages io.delta:delta-core_2.12:2.4.0 \
		--master spark://localhost:7077 \
		--driver-memory 1g \
		--executor-memory 1g \
		--executor-cores 2 \
		build/libs/spark-scala-gradle-bootstrap-2.12.0-all.jar \
		src/main/resources/us-counties-recent.csv \
		/tmp/partitioned-covid-data

### Coverage

https://github.com/scoverage/gradle-scoverage

## Functional Test Examples

https://github.com/scoverage/gradle-scoverage/blob/master/build.gradle#L59C1-L59C52

## Useful Links

- [Spark Docs - Root Page](http://spark.apache.org/docs/latest/)
- [Spark Programming Guide](http://spark.apache.org/docs/latest/programming-guide.html)
- [Spark Latest API docs](http://spark.apache.org/docs/latest/api/)
- [Scala API Docs](http://www.scala-lang.org/api/2.12.1/scala/)
- https://barrelsofdata.com/spark-boilerplate-using-scala

## Issues or Suggestions

https://github.com/mahen-github/spark-scala-gradle-bootstrap/issues

# Learn Spark

https://www.databricks.com/wp-content/uploads/2021/06/Ebook_8-Steps-V2.pdf

# References

https://github.com/spark-examples/spark-scala-examples
