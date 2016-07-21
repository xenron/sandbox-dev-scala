object RichInterfaces extends App {

	trait NameIt {
		def name(): String
	}

	trait Walks extends NameIt {
		def walk() = {
			println(name() + " is having a stroll now")
		}
	}

	trait GoodsMover extends NameIt {
		def moveGoods() = {
			println(name() + " busy moving heavy stuff")
		}
	}

	class Horse extends Walks {
		override def name(): String = "Horse"
	}

	class Donkey extends Walks with GoodsMover {
		override def name(): String = "Donkey"
	}

	val horse = new Horse
		val donkey = new Donkey

		horse.walk()
		donkey.walk()
		donkey.moveGoods()
}

