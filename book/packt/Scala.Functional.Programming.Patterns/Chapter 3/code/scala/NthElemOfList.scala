package chapter03

import scala.annotation.tailrec

/**
 * Created by atul on 25/5/15.
 */
object NthElemOfList extends App {
  def nth(list: List[Int], n: Int): Option[Int] = {
    @tailrec
    def nthElem(list: List[Int], acc: Int): Option[Int] =
      list match {
      case Nil => None
      case head :: tail => {
        if (acc == n) // 1
          Some(head)
        else
          nthElem(tail, acc+1) // 2
      }
    }

    nthElem(list, 0) // 3
  }

  val bigList = 1 to 100000 toList // 4

  println(nth(List(1, 2, 3, 4, 5, 6), 3).getOrElse("No such elem"))
  println(nth(List(1, 2, 3, 4, 5, 6), 300).getOrElse("No such elem"))
  println(nth(bigList, 2333).getOrElse("No such elem"))
}

