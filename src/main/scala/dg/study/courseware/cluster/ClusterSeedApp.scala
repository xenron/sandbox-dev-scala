package dg.study.courseware.cluster

import akka.actor.{Props, ActorSystem}
import com.typesafe.config.ConfigFactory

object ClusterSeedApp {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty)
      startup(Seq("2551", "2552"))
    else
      startup(args)
  }

  def startup(ports: Seq[String]): Unit = {
    ports foreach { port =>
      // Override the configuration of the port
      val config = ConfigFactory.load()

      // Create an Akka system
      val system = ActorSystem("ClusterSystem", config)
      // Create an actor that handles cluster domain events
      system.actorOf(Props[ClusterSeedListener], name = "clusterListener")
    }
  }
}

