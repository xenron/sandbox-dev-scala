package dg.study.courseware.ch05



object ListLast {
  
  def last[T](xs: List[T]) : T = xs match {
    
    case List()  => throw new Error("empty list")
    case List(x) => x
    case h :: t  => last(t)
  }
  
  def main(args: Array[String]): Unit = {
    
    val l = List(1, 2, 3, 4)
    println(last(l))
  }
}