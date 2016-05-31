package dg.study.courseware.ch07



abstract class Bird {
  def name: String
}

trait Flyable {
  def fly() = println("I can fly")
}

trait Swimmable {
  def swim() = println("I can swim")
}

class Pigeon extends Bird with Flyable {
  override def name = "I am a pigeon"
  
  override def fly() = println("I can fly fast")
}

class Penguin extends Bird with Swimmable {
  override def name = "I am a penguin"
}

class Eagle extends Bird with Flyable {
  override def name = "I am an eagle"
}

class Ostrich extends Bird {
  override def name = "I am an ostrich"
}

object Bird extends App {
  val birds = List(new Pigeon, new Pigeon, new Eagle)
  birds.foreach { x => x.fly() }
}