// code-examples/TypeLessDoMore/method-broad-inference-return-script.scala
// Fehler: Lässt sich nicht kompilieren, erfordert den Rückgabetyp
// String für den zweiten "joiner"

def makeList(strings: String*) = {
  if (strings.length == 0)
    List(0)  // #1
  else
    strings.toList
}

val list: List[String] = makeList()  // FEHLER
