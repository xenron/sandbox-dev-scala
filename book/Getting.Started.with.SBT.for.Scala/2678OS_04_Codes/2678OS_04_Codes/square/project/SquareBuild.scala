import sbt._
import sbt.Keys._

object SquareBuild extends Build {

  lazy val square = Project(
    id = "square",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "square",
      organization := "packt",
      version := "1.0",
      scalaVersion := "2.9.2"
      // add other settings here
    )
  )
}
