package chapter03

object ReverseAList1 extends App {
	def reverseList(list: List[Int]): List[Int] = list match {
		case head :: tail => reverseList(tail) :+ head
		case Nil => Nil
	}

	val l = (1 to 20000).toList
	println(reverseList(l))
}
