package chapter03

import scala.annotation.tailrec

object ReverseAList extends App {

	@tailrec
		def reverseList(list: List[Int], acc: List[Int]): List[Int] = list match {
			case head :: tail => reverseList(tail, head :: acc)
				case Nil => acc
		}

	val l = 1 to 20000 toList
	println(reverseList(l, Nil))
}
