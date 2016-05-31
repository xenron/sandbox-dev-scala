package dg.study.courseware.ch04

import java.io._
import scala.io._

object Currying {
  
  def add(x: Int, y: Int) = x + y
  def addSum(x: Int)(y: Int) = x + y
  
  def process[A](filter:A=>Boolean)(list:List[A]):List[A] = {
    lazy val recurse = process(filter) _
 
    list match {
      case head::tail => if (filter(head)) {
                           head::recurse(tail)
                         } else {
                           recurse(tail)
                         }
 
      case Nil => Nil
    }
  }
  
  def withPrintWriter(file: File)(op: PrintWriter => Unit) {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }
 
  def main(args: Array[String]): Unit = {
    val even = (a:Int) => a % 2 == 0
    
    val adds = Function.uncurried(addSum _)
    println(adds(2, 3))
 
    val numbersAsc = 1::2::3::4::5::Nil
    val numbersDesc = 5::4::3::2::1::Nil
 
    println(process(even)(numbersAsc))   // [2, 4]
    println(process(even)(numbersDesc))  // [4, 2]
    
    val processEven = process(even) _
    
    println(processEven(numbersAsc))
    println(processEven(numbersDesc))
    
    val file = new File("abc.txt")
    withPrintWriter(file) {
      writer => writer.println("Hello world")
    }
  }
}