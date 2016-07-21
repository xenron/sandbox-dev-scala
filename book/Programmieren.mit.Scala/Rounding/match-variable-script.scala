// code-examples/Rounding/match-variable-script.scala

import scala.util.Random

val randomInt = new Random().nextInt(10)

randomInt match {
  case 7 => println("Glückliche Sieben!")
  case otherNumber => println("Buh, eine öde " + otherNumber)
}