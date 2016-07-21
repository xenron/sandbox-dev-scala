// code-examples/Concurrency/simple-actor-script.scala

import scala.actors.Actor

class Redford extends Actor {
  def act() {
    println("Viel von dem, was Schauspielerei ausmacht, ist aufmerksam sein.")
  }
}

val robert = new Redford
robert.start