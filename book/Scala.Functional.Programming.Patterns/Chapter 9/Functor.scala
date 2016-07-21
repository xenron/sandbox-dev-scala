
object Functor extends App {

  def functor[X, Y](f: X => Y): List[X] => List[Y] = {
    def fun : (List[X]) => List[Y] = (arg: List[X]) => arg match {
      case Nil => Nil
      case x :: xs => f(x) :: fun(xs)
    }
    fun
  }

  val p = List("Hi", "there")

  def doubleEachChar(s: String) = (for (c <- s)
    yield c + "" + c).toList.mkString

  def numberOfLowerCaseChars(s: String) = s.filter(c => c.isLower).length

  val f1 = functor(doubleEachChar)
  val f2 = functor(numberOfLowerCaseChars)

  println(f1(p))
  println(f2(p))
}

