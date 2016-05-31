package dg.study.courseware.ch04



object Closure extends App {
  
  var sum = 0;
  val l = List(10, 20, 4, 0, -4, -20)
  
  l.foreach { sum += _ }
  println(sum)
}