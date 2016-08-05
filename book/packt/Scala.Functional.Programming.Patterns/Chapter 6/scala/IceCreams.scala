import scala.language.implicitConversions

object IceCreams extends App {
	sealed trait IceCreamType {
		def price: Double
	}

	case object Vanilla extends IceCreamType {
		val price = 10.0
	}
	case object Mango extends IceCreamType {
		val price = 20.0
	}

	implicit def iceCreamPriceWrapper(iceCreamType: IceCreamType): Double =
		iceCreamType.price

	case class IceCream(price: Double)

	def add(c: IceCream)(p: Double) =
		c.copy(price = c.price + p)

	def addNuts(c: IceCream) = add(c)(15)
	def addJelly(c: IceCream) = add(c)(25)
	def addHoney(c:IceCream) = add(c)(30)

	val withNuts = addNuts _
	val withJelly = addJelly _
	val withHoney = addHoney _

	val order1 = withJelly(withNuts(withHoney(IceCream(Vanilla))))
	println(order1.price)
	val order2 = withJelly(withNuts(IceCream(Mango)))
	println(order2.price)
}
