package chapter03

/**
 * Created by atul on 25/5/15.
 */
object SumIt extends App {
	def sumIt(list: List[Int]): Int = list match {
		case Nil => 0
			case head :: tail => head + sumIt(tail)
	}

	val l = (1 to 5).toList
		println(sumIt(l))
}

