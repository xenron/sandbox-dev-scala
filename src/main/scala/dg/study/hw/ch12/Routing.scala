package dg.study.hw.ch12

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.Terminated
import akka.routing.ActorRefRoutee
import akka.routing.Router
import akka.routing.RoundRobinRoutingLogic

case class Reply(msg: String)

class Worker extends Actor with ActorLogging {

  def receive = {
    case name: String => 
      println("instruction received from master: " + name)
      sender().!(Reply("Hello " + name))(context.parent)
  }
}

class Master extends Actor with ActorLogging{
  var router = {
    val routees = Vector.fill(5) {
      val r = context.actorOf(Props[Worker])
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  def receive = {
    case w: String =>
      router.route(w, self)
    case Reply(msg) => 
      println("reply received: " + msg)
    case Terminated(a) => {
      router = router.removeRoutee(a)
      val r = context.actorOf(Props[Worker])
      context watch r
      router = router.addRoutee(r)
    }
  }
}

object Master {
  val props = Props(new Master)
}

object RouterApp extends App {

  val system = ActorSystem("routerSystem");
  val master = system.actorOf(Master.props, "router");

  master ! "Jim"
  master ! "Tom"
  master ! "John"

  Thread.sleep(500)
  system.shutdown()
}