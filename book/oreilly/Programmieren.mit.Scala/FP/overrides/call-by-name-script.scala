// code-examples/FP/overrides/call-by-name-script.scala

def whileFantastic(conditional: => Boolean)(f: => Unit) {
  if (conditional) {
    f
    whileFantastic(conditional)(f)
  }
}

var count = 0

whileFantastic(count < 5) {
  println("immer noch phantastisch")
  count += 1
}