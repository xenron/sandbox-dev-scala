// code-examples/TypeLessDoMore/method-nested-return-script.scala
// FEHLER: Lässt sich nicht kompilieren, solange
// upCase nicht den Rückgabetyp String hat.

def upCase(s: String) = {
  if (s.length == 0)
    return s
  else
    s.toUpperCase()
}

println( upCase("") )
println( upCase("Hallo") )
