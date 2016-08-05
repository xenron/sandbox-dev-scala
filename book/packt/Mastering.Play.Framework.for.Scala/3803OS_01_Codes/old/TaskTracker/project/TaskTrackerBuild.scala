import sbt._
import Keys._
import play.Project._

object TaskTrackerBuild extends Build {

  val appName = "TaskTracker"
  val appVersion = "1.0"

  val scalaVersion = "2.10.3"

  val appDependencies = Seq(
    // Add your project dependencies here
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(

  )
}