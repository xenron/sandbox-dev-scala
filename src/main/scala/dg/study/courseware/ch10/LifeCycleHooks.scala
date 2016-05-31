package dg.study.courseware.ch10

import akka.actor._
import akka.actor.SupervisorStrategy.{ Stop, Resume, Restart }

class LifeCycleHooks extends Actor with ActorLogging {
  
  println("Constructor")
  
  override def preStart() = println("pre-start")
  
  override def postStop() = println("post-stop")
  
  override def preRestart(reason: Throwable, msg: Option[Any]) = {
    println("pre-restart")
    println("reason: " + reason.getMessage)
    
    msg match {
      case Some(content) => println("msg: " + content)
      case None => println("no msg")
    }
    
    super.preRestart(reason, msg)
  }
  
  override def postRestart(reason: Throwable) = {
    println("post-restart")
    super.postRestart(reason)
  }
  
  def receive = {
    case "restart" =>
      println("restart received")
      throw new IllegalStateException("forced restart")
    case PoisonPill => println("poison pill")
  }
}

class SupervisingActor extends Actor with ActorLogging {
  
  val lifeCycleActorRef = this.context.actorOf(Props[LifeCycleHooks], "LifeCycleHooks")
  context.watch(lifeCycleActorRef)
  
  def receive = {
    case msg: Any => 
      lifeCycleActorRef.tell(msg, self)
  }
  
  override val supervisorStrategy = OneForOneStrategy(){
      case _: IllegalStateException =>
        Restart
  }
}

object LifeCycleApp extends App {
  val system = ActorSystem("lifeCycleSystem")
  val supervisorActorRef = system.actorOf(Props[SupervisingActor], "SupervisingActor")
  
  supervisorActorRef ! "msg"
  supervisorActorRef ! "restart"
  Thread.sleep(500)
  system.stop(supervisorActorRef)
}