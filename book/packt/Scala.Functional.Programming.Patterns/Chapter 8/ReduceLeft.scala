import scala.annotation.tailrec

object ReduceLeft extends App {

  def reduceLeft(l: List[String], f: (String, String) => String) = {
    @tailrec
    def reduceIt(acc: String, list: List[String]) : String = list match {
      case Nil => acc
      case x :: xs => reduceIt(f(acc, x), xs)
    }
    reduceIt(l(0), l.drop(1))
  }


  val nums = (1 to 20).toList map { _.toString }

  def f(acc: String, s: String) = s"(${acc} - ${s})"

  val result = reduceLeft(nums, f)

  println(result)
}


