
object Monad extends App {

  def monad[X, Y](f: X => List[Y]): List[X] => List[Y] = {

      def fun : (List[X]) => List[Y] = (arg: List[X]) => arg match {
        case Nil => Nil
        case x :: xs => f(x) ::: fun(xs)
      }
      fun
    }

  def f1(n: Int) = (1 to n).toList

  val p = monad(f1)

  println(p(List(7,8,9)))
}

