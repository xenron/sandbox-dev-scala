// code-examples/Rounding/match-boolean-script.scala

val bools = List(true, false)

for (bool <- bools) {
  bool match {
    case true => println("Vorderseite")
    case false => println("Rückseite")
    case _ => println("etwas Anderes als die Vorder- oder Rückseite (nanu)")
  }
}
