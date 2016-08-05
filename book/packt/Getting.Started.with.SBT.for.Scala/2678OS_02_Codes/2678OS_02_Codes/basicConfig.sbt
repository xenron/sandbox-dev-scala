// set the name of the project
name := "Project Name"

// set the version of the project
version := "versionNumber"

// set the organization/group ID for the project
organization := "org.projectName"

// set whether the current version of the project is a snapshot
isSnapshot := true/false

// specify the version of Scala used by the project
scalaVersion := "ScalaVersionUsed"

// add additional libraries used in the project
libraryDependencies ++= Seq(IvyModuleId)

// change the interval between polling for source changes when using continuous execution
// default value is 500
pollInterval := intervalInMilliSecond

// add maven-style repositories
resolvers ++= Seq("name" at "url")

// set the repository to which sbt should publish
publishTo := Some("name" at "url")

// set whether to update dynamic revisions (including -SNAPSHOT versions)
offline := true/false

// enable or disable parallel task execution -- this is enabled by default 
parallelExecution := true/false

// set whether the project should be published in Maven/Ivy style
publishMavenStyle := true/false

