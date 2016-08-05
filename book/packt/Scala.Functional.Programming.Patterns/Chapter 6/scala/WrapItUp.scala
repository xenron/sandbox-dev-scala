object WrapItUp extends App {
  def attach_logger[X, Y](f: (X) => Y)(arg: X): Y = {
    println("arg = " + arg.toString)
    println("Calling the function...")
    val result = f(arg)
    println("function call returned...")

    result
  }

  def f1(i: Integer): Integer =
    i + 10

  def f2(s: String): String =
    s + " " + s

  val f1WithArgsLogged = attach_logger(f1) _ // attach argument loggin
  val f2WithArgsLogged = attach_logger(f2) _ // without touching the function

  println(s"Evaluating just f1 itself = ${f1(2)}\n")
  println(s"Evaluating f1 with logging = ${f1WithArgsLogged(2)}\n")
  println(s"Evaluating just f2 itself = ${f2("hello")}\n")
  println(s"Evaluating f2 with logging ${f2WithArgsLogged("hello")}\n")
}

