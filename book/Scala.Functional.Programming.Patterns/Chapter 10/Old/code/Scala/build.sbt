name := "packt"

version := "1.0"

organization := "com.packt" //<co id="example-app-info"/>

libraryDependencies ++= {
  val akkaVersion       = "2.3.10" //<co id="akkaVersion"/>
  Seq(
    "com.typesafe.akka" %% "akka-actor"      % akkaVersion, //<co id="actorDep"/>
    "com.typesafe.akka" %% "akka-slf4j"      % akkaVersion,
    "ch.qos.logback"    %  "logback-classic" % "1.1.3",
    "com.typesafe.akka" %% "akka-testkit"    % akkaVersion   % "test"
  )
}
