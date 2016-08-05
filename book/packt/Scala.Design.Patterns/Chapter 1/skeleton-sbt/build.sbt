organization := "com.ivan.nikolov"

name := "skeleton-sbt"

scalaVersion := "2.10.5"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

javaOptions ++= Seq("-target", "1.8", "-source", "1.8")

publishMavenStyle := true

libraryDependencies ++= {
  val sparkVersion = "1.2.2"
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
    "com.datastax.spark" %% "spark-cassandra-connector" % "1.2.1",
    "org.scalatest" %% "scalatest" % "2.1.3" % "test",
    "org.mockito" % "mockito-all" % "1.9.5" % "test" // mockito for tests
  )
}
    
