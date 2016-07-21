object ReduceRight extends App {
  def reduceRight(l: List[String], f: (String, String) =>
    String): String = l match {
    case Nil => throw new IllegalArgumentException("Bad List")
    case y :: Nil => y
    case x :: xs => f(x, reduceRight(xs, f))
  }

  val nums = (1 to 3).toList map { _.toString }

  def f(s: String, acc: String) = s"(${s} - ${acc})"

  val result = reduceRight(nums, f)

  println(result)
}


