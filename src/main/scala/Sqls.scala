package com.example

import org.apache.spark.sql.{DataFrame, SparkSession}

object Sqls {

  /**
   * The DataFrame will return 3 Highest Salary (same salary will not be considered as same)
   * @param employeeTable: Employee Table
   * @param departmentTable: Department Table
   * @param spark: Spark Session
   * @return
   */

  def actionOne(employeeTable: DataFrame,
             departmentTable: DataFrame)
            (implicit spark: SparkSession): DataFrame  = {

    employeeTable.createOrReplaceTempView("employeeTableDfvw")
    departmentTable.createOrReplaceTempView("departmentTableDfvw")

    spark.sql(
      s"""
         |SELECT
         |  *
         |FROM
         |    (SELECT
         |        a.Name
         |       ,b.name as Department
         |       ,a.Salary
         |       ,ROW_NUMBER() OVER(PARTITION BY b.name ORDER BY a.SALARY DESC) AS Row_Number
         |     FROM
         |       employeeTableDfvw as a
         |     JOIN
         |       departmentTableDfvw as b
         |     ON
         |       a.DepartmentId = b.Id)
         |WHERE Row_Number <= 3
         |""".stripMargin
    )
  }

  /*

  def actionTwo(employeeTable: DataFrame,
             departmentTable: DataFrame)
            (implicit spark: SparkSession): DataFrame  = {

    employeeTable.createOrReplaceTempView("employeeTableDfvw")
    departmentTable.createOrReplaceTempView("departmentTableDfvw")

    spark.sql(
      s"""
         |SELECT
         |      a.Name AS Department
         |    , b.Name AS Employee
         |    , b.Salary
         |FROM departmentTableDfvw a
         |JOIN employeeTableDfvw b
         |ON a.Id = b.DepartmentId
         |WHERE (
         |    SELECT count(DISTINCT Salary)
         |    FROM employeeTableDfvw
         |    WHERE DepartmentId = a.Id and Salary > b.Salary) < 3
         |""".stripMargin
    )
  }

   */
}
