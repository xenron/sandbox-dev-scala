object CompareAnimals extends App {

  trait Walks {
    def name: String

    def walk() =
      println(name + " is having a stroll now")
  }

  trait GoodsMover {
    def name: String

    def moveGoods() =
      println(name + " busy moving heavy stuff")
  }

  abstract class Animal(val rating: Int) extends Ordered[Animal]

  class Horse(rating: Int) extends Animal(rating) with Walks {
    override def name(): String = "Horse"

    override def compare(that: Animal): Int = rating - that.rating
  }

  class Donkey(rating: Int) extends Animal(rating) with
  Walks with GoodsMover {
    override def name(): String = "Donkey"

    override def compare(that: Animal): Int = rating - that.rating
  }

  class Mule(rating: Int) extends Animal(rating) with Walks with GoodsMover with Ordered[Animal] {
    override def name(): String = "Donkey"

    override def compare(that: Animal): Int = rating - that.rating
  }

  val horse = new Horse(1)
  val donkey = new Donkey(2)

  if (horse < donkey) {
    println("Donkeys are more useful than horses - breed Donkeys")
  } else if (horse > donkey) {
    println("Horses are more useful than donkeys - breed Horses")
  }
}

