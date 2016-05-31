package dg.study.courseware.ch05



object ListInit {
  
  def init[T](xs: List[T]): List[T] = xs match {
    
    case List()  => throw new Error("list is empty")
    case List(x) => List()
    case y :: ys => y :: init(ys)
  }
  
  def main(args: Array[String]): Unit = {
    
    val l = List(1, 2, 3, 4)
    println(init(l))
  }
}