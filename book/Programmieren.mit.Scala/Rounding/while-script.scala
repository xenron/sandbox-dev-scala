// code-examples/Rounding/while-script.scala
// ACHTUNG: Dieses Skript läuft LAAAAAAAAAAAAANGE!

import java.util.Calendar

def isFridayThirteen(cal: Calendar): Boolean = {
  val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
  val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)

  // Scala gibt das Ergebnis des letzten Ausdrucks
  // in einer Methode zurück
  (dayOfWeek == Calendar.FRIDAY) && (dayOfMonth == 13)
}

while (!isFridayThirteen(Calendar.getInstance())) {
  println("Heute ist nicht Freitag der 13. Wie öde.")
  // einen Tag lang schlafen
  Thread.sleep(86400000)
}