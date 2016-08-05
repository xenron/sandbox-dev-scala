// code-examples/Concurrency/pattern-match-actor-script.scala

import scala.actors.Actor
import scala.actors.Actor._

val fussyActor = actor {
  loop {
    receive {
      case s: String => println("String erhalten: " + s)
      case i: Int => println("Int erhalten: " + i.toString)
      case _ => println("Keine Ahnung, was ich da erhalten habe.")
    }
  }
}

fussyActor ! "Hallo"
fussyActor ! 23
fussyActor ! 3.33
