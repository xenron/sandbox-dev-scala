// code-examples/TypeLessDoMore/semicolon-example-script.scala

// Das Gleichheitszeichen am Zeilenende weist auf weiteren Code
// in der Folgezeile hin.
def equalsign = {
  val reallySuperLongValueNameThatGoesOnForeverSoYouNeedANewLine =
    "wow that was a long value name"

  println(reallySuperLongValueNameThatGoesOnForeverSoYouNeedANewLine)
}

// Die öffnenden geschweifte Klammer am Zeilenende weist auf
// weiteren Code in der Folgezeile hin.
def equalsign2(s: String) = {
  println("equalsign2: " + s)
}

// Ein Komma, Operator usw. am Zeilenende weist auf weiteren Code
// in der Folgezeile hin.
def commas(s1: String,
           s2: String) = {
  println("comma: " + s1 +
          ", " + s2)
}
