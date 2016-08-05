// code-examples/TypeLessDoMore/method-recursive-return-script.scala
// FEHLER: Lässt sich nicht kompilieren, solange "fact" keinen
// Rückgabetyp hat

def factorial(i: Int) = {
  def fact(i: Int, accumulator: Int) = {
    if (i <= 1)
      accumulator
    else
      fact(i - 1, i * accumulator)  // FEHLER
  }
  
  fact(i, 1)
}

