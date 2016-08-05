// code-examples/Concurrency/sleepingbarber/barbershop-simulator.scala

package sleepingbarber

import scala.actors.Actor._
import scala.collection.{immutable, mutable}
import scala.util.Random

object BarbershopSimulator {
  private val random = new Random()
  private val customers = new mutable.ArrayBuffer[Customer]()
  private val shop = new Shop()

  def generateCustomers {
    for (i <- 1 to 20) {
      val customer = new Customer(i)
      customer.start()
      customers += customer
    }

    println("[!] " + customers.size + " Kunden generiert.")
  }

  // Die Kunden treffen in zufälligen Abständen ein
  def trickleCustomers {
    for (customer <- customers) {
      shop ! customer
      Thread.sleep(random.nextInt(400))
    }
  }

  def tallyCuts {
    // Warte auf den Abschluss eventueller nebenläufiger Aktionen.
    Thread.sleep(2000)

    val shornCount = customers.filter(c => c.shorn).size
    println("[!] " + shornCount + " Kunden haben heute eine Haarschnitt bekommen.")
  }

  def main(args: Array[String]) {
    println("[!] Friseursalon-Simulation wird gestartet.")
    shop.start()

    generateCustomers
    trickleCustomers
    tallyCuts

    System.exit(0)
  }
}