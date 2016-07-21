// code-examples/Concurrency/actor-mailbox-script.scala

import scala.actors.Actor
import scala.actors.Actor._

val countActor = actor {
  loop {
    react {
      case "wie viele?" => {
        println("Ich habe " + mailboxSize.toString + " Nachrichten in meiner Mailbox.")
      }
    }
  }
}

countActor ! 1
countActor ! 2
countActor ! 3
countActor ! "wie viele?"
countActor ! "wie viele?"
countActor ! 4
countActor ! "wie viele?"
