// code-examples/Rounding/match-type-script.scala

val sundries = List(23, "Hallo", 8.5, 'q')

for (sundry <- sundries) {
  sundry match {
    case i: Int => println("Integer erhalten: " + i)
    case s: String => println("String erhalten: " + s)
    case f: Double => println("Double erhalten: " + f)
    case other => println("etwas Anderes erhalten: " + other)
  }
}
