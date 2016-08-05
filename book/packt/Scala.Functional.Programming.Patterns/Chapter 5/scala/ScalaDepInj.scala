object ScalaDepInj extends App {

  abstract class Animal(val rating: Int, var inoculated:
  Boolean = false) {
    def name(): String

    def alreadyInoculated() = inoculated

    def setInoculated(b: Boolean) = {
      inoculated = b
    }
  }

  trait Vet {
    // 1
    def name(): String

    def inoculate(animal: Animal) = {
      println("Innoculating " + animal.name)
    }
  }

  trait ChoosyVet extends Vet {
    // 2
    def alreadyInoculated(): Boolean

    abstract override def inoculate(animal: Animal) = {
      if (!alreadyInoculated()) // filter out already
      // inoculated animals
        println("Innoculating " + animal.name)
    }
  }

  trait Inoculate {
    this: Animal with Vet =>
    // 3
    def setInoculated(b: Boolean)

    def giveInoculation() = {
      inoculate(this)
      setInoculated(true)
    }

    def name(): String
  }

  class Horse(rating: Int) extends Animal(rating) {
    override def name(): String = "Horse"
  }

  // driver
  val h = new Horse(1) with ChoosyVet with Inoculate // 4

  h.giveInoculation() // prints message
  h.giveInoculation() // filtered out
}

