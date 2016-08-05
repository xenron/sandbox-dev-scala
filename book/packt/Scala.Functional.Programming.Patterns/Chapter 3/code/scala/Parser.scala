package chapter03

import scala.annotation.tailrec

object Parser extends App {

	val Number = """^(\d+).*""".r      // 1
		val LParen = """^[(].*""".r

		def factor(f: String): (String, Int) = f match {
			case Number(d) => (f.drop(d.length), d.toInt) // 2
				case LParen(_*) => {
					val p = expr(f.drop(1), 0)  // 3
						val e = p._1
						if (e.take(1) == ")") {  // 4
							(e.drop(1), p._2)
						} else {
							throw new IllegalArgumentException("Right bracket missing")
						}
				}
			case _ => throw new IllegalArgumentException("Number or sub-expression expected")
		}

	@tailrec
		def term(t: String, acc: Int): (String, Int) = {
			val p = factor(t)
				val e = p._1

				if (e.take(1) == "*") {                 // 5
					term(e.drop(1), acc * p._2)       // 6
				} else {
					(e, acc * p._2)                           // 7
				}
		}

	@tailrec
		def expr(s: String, acc: Int): (String, Int) = {
			val p = term(s, 1)
				val e = p._1

				if (e.take(1) == "+") {                  // 8
					expr(e.drop(1), acc + p._2)         // 9
				} else {
					(e, acc + p._2)                             // 10
				}
		}

	def expr(s: String): Int = {
		val e = expr(s, 0)
			e._2
	}

	val p = expr("(1+2)*3*(2+4)")
	println(p)
}

