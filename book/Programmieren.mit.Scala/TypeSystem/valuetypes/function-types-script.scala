// code-examples/TypeSystem/valuetypes/function-types-script.scala

val capitalizer = (s: String) => s.toUpperCase

val capitalizer2 = new Function1[String,String] {
  def apply(s: String) = s.toUpperCase
}

println( List("Programmieren", "mit", "Scala") map capitalizer)
println( List("Programmieren", "mit", "Scala") map capitalizer2)
