package com.packt

import akka.actor.{ ActorSystem , Actor, Props }

object Main extends App {

  implicit val system = ActorSystem("packt")

  val fileFinder = system.actorOf(Props(new FileFinder), "FileFinder")

  import FileFinder.FileFinderMsg

  fileFinder ! FileFinderMsg("this", "../data")

}
