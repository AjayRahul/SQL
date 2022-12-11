package com.example

import org.apache.spark.sql.{DataFrame, SparkSession}

object Driver extends App {

  val spark: SparkSession = SparkSession
    .builder()
    .master("local[*]")
    .appName("spark_postgres_connection_driver")
    .getOrCreate()

  def EmployeeTable: DataFrame  = spark.read
    .format("jdbc")
    .option("url", "jdbc:postgresql://localhost:5432/DB1")
    .option("dbtable", "\"Employee\"")
    .option("user", "postgres")
    .option("password", "1234@Ajay")
    .load()

  print("Printing Employee Table and it's count is ", EmployeeTable.count())
  EmployeeTable.show()

  def DepartmentTable: DataFrame = spark.read
    .format("jdbc")
    .option("url", "jdbc:postgresql://localhost:5432/DB1")
    .option("dbtable", "\"Department\"")
    .option("user", "postgres")
    .option("password", "1234@Ajay")
    .load()

  DepartmentTable.show()
  print("Printing Department Table and it's count is ", DepartmentTable.count())

  val actionDf = Sqls.actionOne(EmployeeTable, DepartmentTable)(spark)

  actionDf.show()

  actionDf.write
    .format("jdbc")
    .option("url", "jdbc:postgresql://localhost:5432/DB1")
    .option("dbtable", "\"EmployeeJoinDepartment\"")
    .option("user", "postgres")
    .option("password", "1234@Ajay")
    .save()
}
