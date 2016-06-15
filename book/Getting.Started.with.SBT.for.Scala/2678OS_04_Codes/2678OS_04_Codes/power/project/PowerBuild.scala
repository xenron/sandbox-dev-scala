import sbt._
import sbt.Keys._

object PowerBuild extends Build {
 
  lazy val square = Project(
    id = "square",
    base = file("square"),
    settings = Project.defaultSettings ++ Seq(
      name := "square",
      organization := "dep",
      version := "1.0",
      scalaVersion := "2.9.2"
    )
  )

  lazy val power = Project(
    id = "power",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "Power",
      organization := "packt", 
      version := "1.0",
      scalaVersion := "2.9.2"
    )
  )dependsOn(square)
}
