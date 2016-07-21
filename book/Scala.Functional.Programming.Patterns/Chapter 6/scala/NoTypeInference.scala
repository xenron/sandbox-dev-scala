
object NoTypeInference extends App {
  def firstMatching[T](xs: List[T], f: (T) => Boolean): Option[T] = xs match {
    case Nil => None
    case x :: ts => if (f(x)) Some(x) else firstMatching(ts, f)
  }

  println(firstMatching(List(1,2,3,4), (x: Int) => x < 2))
  println(firstMatching(List("hi", "hello", "some", "one"), (x:String) => x.length >= 5))
}

