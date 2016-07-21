// code-examples/Concurrency/factory-actor-script.scala

import scala.actors.Actor
import scala.actors.Actor._

val paulNewman = actor {
  println("Um ein Schauspieler zu sein, muss man Kind sein.")
}