// code-examples/IntroducingScala/shapes-actor.scala

package shapes {
  import scala.actors._
  import scala.actors.Actor._

  object ShapeDrawingActor extends Actor {
    def act() {
      loop {
        receive {
          case s: Shape => s.draw()
          case "exit"   => println("Beenden..."); exit
          case x: Any   => println("Fehler: Unbekannte Mitteilung! " + x)
        }
      }
    }
  }
}
