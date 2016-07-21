// code-examples/Rounding/try-catch-script.scala

import java.util.Calendar

val then = null
val now = Calendar.getInstance()

try {
  now.compareTo(then)
} catch {
  case e: NullPointerException => println("Eins von beiden ist null!"); System.exit(-1)
  case unknown => println("Unbekannte Exception " + unknown); System.exit(-1)
} finally {
  println("Es hat alles funktioniert.")
  System.exit(0)
}
