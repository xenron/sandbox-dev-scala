package chapter03

import scala.annotation.tailrec

object CountTR extends App {
	def count(list: List[Int]): Int = {
		@tailrec // 1
			def countIt(l: List[Int], acc: Int): Int = l match {
				case Nil => acc // 2
					case head :: tail => countIt(tail, acc + 1) // 3
			}
		countIt(list, 0)
	}

	val l = List(1, 2, 3, 4, 5)
	println(count(l)) // prints 5
}


