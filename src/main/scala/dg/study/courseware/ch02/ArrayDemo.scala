package dg.study.courseware.ch02



object ArrayDemo extends App {
  
  val arr = new Array[String](3)
  arr(0) = "test1"
  arr(1) = "test2"
  arr(2) = "test3"
  
  println("Initial values")
  println(arr(0))
  println(arr(1))
  println(arr(2))
  
  println("Updated with ()")
  arr(2) = "test4"
  println(arr(2))
  
  println("Updated with update")
  arr.update(1, "test5")
  println(arr(1))
  
  println("Iterating with for")
  for (ele <- arr) {
    println(ele)
  }
  
  println("Iterating with foreach")
  arr.foreach { x => println(x) }
}