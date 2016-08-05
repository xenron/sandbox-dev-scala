import sbt._
import Keys._

object DemoBuild extends Build
{
  lazy val introduction =
    Project("introduction", file("."))
      .configs( IntegrationTest )
      .settings( Defaults.itSettings : _*)
      .settings(
	name := "Introduction",

	version := "1.0",

	scalaVersion := "2.9.1",

	resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  			"releases" at "http://oss.sonatype.org/content/repositories/releases"),
 
	libraryDependencies ++= Seq("org.specs2" %% "specs2" % "1.12.3" % "it,test")
 )

}
