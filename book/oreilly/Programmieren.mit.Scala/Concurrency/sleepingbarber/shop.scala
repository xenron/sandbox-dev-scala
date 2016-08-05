// code-examples/Concurrency/sleepingbarber/shop.scala

package sleepingbarber

import scala.actors.Actor
import scala.actors.Actor._
import scala.collection.mutable.Queue

class Shop extends Actor {
  var customerCount = 0
  val barber = new Barber(this)
  barber.start

  def helpCustomer(customer: Customer) {
    if (customerCount >= 3) {
      println("[b] Zu wenig Stühle; Kunde " + customer.id + " wird abgelehnt.")
    } else {
      println("[b] Kunde " + customer.id + " wird angenommen.")
      barber ! customer
      customerCount += 1
    }
  }

  def act() {
    println("[s] Der Salon ist geöffnet")

    loop {
      react {
        case customer: Customer => helpCustomer(customer)
        case BeginHaircut => if (customerCount > 0) customerCount -= 1
      }
    }
  }
}