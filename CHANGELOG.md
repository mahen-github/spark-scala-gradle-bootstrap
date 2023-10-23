
# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## [1.0.0] - 2023-10-23

First release version of the project

### Added
- support for gradle 7.6, java 11, spark 3.4.1, scala 2.12 and delta 2.4.0
- support to read file:// and s3://
- Added CHANGELOG.md
- Added example spark code
- Added example classes `dev.template.spark.Main` and `dev.template.spark.CovidDataPartitioner`
- Added tests for the above classes
- Added scalaCodeCoverage
- Added spotless, checkstyle, and spotbugs