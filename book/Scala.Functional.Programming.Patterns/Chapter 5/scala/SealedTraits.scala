object SealedTraits extends App {

	sealed trait Num

	final case class One() extends Num

	final case class Two() extends Num

	val p: Num = One()

	p match {
		case _: One => println("1")
	}

}

