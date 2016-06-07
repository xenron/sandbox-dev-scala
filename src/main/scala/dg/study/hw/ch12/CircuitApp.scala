package dg.study.hw.ch12

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.Terminated
import akka.routing.ActorRefRoutee
import akka.routing.Router
import akka.routing.RoundRobinRoutingLogic
import akka.pattern.CircuitBreaker
import akka.pattern.pipe

import scala.concurrent.duration._
import scala.concurrent.Future
import scala.util.Random

import java.util.{Calendar, Date}

object CircuitWorker {
  val props = Props(new CircuitWorker)
}

class CircuitWorker extends Actor with ActorLogging {

  import context.dispatcher

  var random = new Random(Calendar.getInstance().getTime().getTime())

  val breaker = new CircuitBreaker(context.system.scheduler,
                                   maxFailures = 2,
                                   callTimeout = 1.milliseconds,
                                   resetTimeout = 1.seconds).onOpen(notifyOnOpen()).onClose(notifyOnClose())

  def notifyOnOpen() : Unit = {

    log.warning("circuit open")
  }

  def notifyOnClose() : Unit = {

    log.warning("circuit closed")
  }

  def reply(name: String ) = {

    if (random.nextDouble() > 0.4)
      Thread.sleep(100)
    "Hello " + name
  }

  def receive = {
    case name: String => 
      println("instruction received from master: " + name)

      val f = breaker.withCircuitBreaker(Future(reply(name)))
      f onSuccess {
        case s: String => sender() ! Reply(s)
      }
  }
}

class CircuitMaster extends Actor with ActorLogging{

  val worker = context.actorOf(CircuitWorker.props)

  def receive = {
    case w: String =>
      worker ! w
    case Reply(msg) => 
      println("reply received: " + msg)
  }
}

object CircuitMaster {
  val props = Props(new CircuitMaster)
}

object CircuitApp extends App {

  val system = ActorSystem("routerSystem");
  val master = system.actorOf(CircuitMaster.props, "router");

  master ! "Jim"
  master ! "Tom"
  master ! "John"
  master ! "Jack"
  master ! "Jane"
}