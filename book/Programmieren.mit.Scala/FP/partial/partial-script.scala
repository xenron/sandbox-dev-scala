// code-examples/FP/partial/partial-script.scala

def concatUpper(s1: String, s2: String): String = (s1 + " " + s2).toUpperCase

val c = concatUpper _
println(c("kurze", "Hosen"))

val c2 = concatUpper("kurze", _: String)
println(c2("Hosen"))