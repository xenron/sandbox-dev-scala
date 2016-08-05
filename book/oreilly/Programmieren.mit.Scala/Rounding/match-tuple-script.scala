// code-examples/Rounding/match-tuple-script.scala

val tupA = ("Good", "Morning!")
val tupB = ("Guten", "Tag!")

for (tup <- List(tupA, tupB)) {
  tup match {
    case (thingOne, thingTwo) if thingOne == "Good" =>
        println("Ein Zweier-Tupel, das mit 'Good' beginnt.")
    case (thingOne, thingTwo) =>
        println("Dies hat zwei Elemente: " + thingOne + " und " + thingTwo)
  }
}