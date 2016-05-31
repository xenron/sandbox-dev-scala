package dg.study.courseware.ch07

import scala.collection.mutable.ArrayBuffer
import dg.study.courseware.ch07.example._

abstract class IntQueue {
  def get(): Int
  def put(x: Int)
}

class BasicIntQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]
  
  override def get(): Int = buf.remove(0)
  
  override def put(x: Int) = buf += x
}

object QueueApp extends App {
  val queue = new BasicIntQueue with Doubling
  queue.put(2)
  queue.put(3)
  println(queue.get())
  println(queue.get())
  
  val queue2 = new BasicIntQueue with Doubling with Incrementing with Filtering
  queue2.put(10)
  queue2.put(5)
  queue2.put(-10)
  
  println(queue2.get())
  println(queue2.get())
  
  println(dg.study.courseware.ch07.example.name)
}