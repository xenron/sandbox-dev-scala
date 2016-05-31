package dg.study.courseware.ch09

import akka.actor._
import akka.event.Logging

object AkkaTest extends App {
  
  println("Hello world")
  
  val system = ActorSystem("actorSystem")
  println("actor system created")
  
  println(system.settings)
  
  val myActor = system.actorOf(Props[HelloActor], "helloActor")
  myActor ! "Jack"
  myActor ! 12
}

class HelloActor extends Actor {
  val log = Logging(context.system, this)
  
  def receive = {
    case s: String => println("Hello " + s)
    case _ => println("Hello world")

    log.info("message received")
  }
}