// code-examples/IntroducingScala/upper2-script.scala

object Upper {
  def upper(strings: String*) = strings.map(_.toUpperCase())
}

println(Upper.upper("Ein", "erstes", "Scala", "Programm"))
