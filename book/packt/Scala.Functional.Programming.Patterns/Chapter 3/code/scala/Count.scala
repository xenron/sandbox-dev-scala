package chapter03

object Count extends App {
	def count(list: List[Int]): Int = list match {
		case Nil => 0 // 1
			case head :: tail => 1 + count(tail) // 2
	}

	val l = List(1, 2, 3, 4, 5)
		println(count(l)) // prints 5
}

