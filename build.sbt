name := "spark_postgres_connection"

version := "0.1"

scalaVersion := "2.13.10"

idePackagePrefix := Some("com.example")

libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.2.1"

libraryDependencies += "org.apache.spark" %% "spark-core" % "3.2.1"

libraryDependencies += "org.postgresql" % "postgresql" % "42.2.27"
