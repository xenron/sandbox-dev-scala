// code-examples/Concurrency/sleepingbarber/barber.scala

package sleepingbarber

import scala.actors.Actor
import scala.actors.Actor._
import scala.util.Random

case object BeginHaircut

class Barber (val shop:Shop) extends Actor {
  private val random = new Random()

  def cutHair(customer: Customer) {
      shop ! BeginHaircut
      println("[b] Beginn Haarschnitt für Kunde " + customer.id)
      Thread.sleep(100 + random.nextInt(400))
      customer ! Haircut
  }

  def act() {
    loop {
      react {
        case customer: Customer => cutHair(customer)
      }
    }
  }
}