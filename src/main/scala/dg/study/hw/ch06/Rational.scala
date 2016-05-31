package dg.study.hw.ch06

class Rational(n: Int, d: Int) {

  def this(d: Int) = {
    this(0, d)
  }

  private def gcd(x: Int, y: Int): Int = {
    if (x == 0) y
    else if (x < 0) gcd(-x, y)
    else gcd(y % x, x)
  }

  private val g = gcd(n, d)

  val numerator: Int = n / g
  val denominator: Int = d / g

  def +(target: Rational) = new Rational(numerator * target.denominator + target.numerator * denominator, denominator * target.denominator)

  def -(target: Rational) = new Rational(numerator * target.denominator - target.numerator * denominator, denominator * target.denominator)

  def *(target: Rational) = new Rational(numerator * target.numerator, denominator * target.denominator)

  def /(target: Rational) = new Rational(numerator * target.denominator, denominator * target.numerator)

  def +(target: Int) = new Rational(numerator + target * denominator, denominator)

  def -(target: Int) = new Rational(numerator - target * denominator, denominator)

  def *(target: Int) = new Rational(numerator * target, denominator)

  def /(target: Int) = new Rational(numerator, denominator * target)

  def unary_~ = new Rational(denominator, numerator)

  override def toString() =
    "Rational: [" + numerator + " / " + denominator + "]"
}

object RationalTest {
  def main(args: Array[String]): Unit = {
    val r1 = new Rational(2, 6)
    val r2 = new Rational(2, 5)
    val r3 = r1 - r2
    val r4 = r1 + r2
    Console.println("r1 = " + r1)
    Console.println("r2 = " + r2)
    Console.println("r3 = " + r3)
    Console.println("r4 = " + r4)

    Console.println("r1 + 1 = " + (r1 + 1))
    Console.println("r1 - 1 = " + (r1 - 1))
    Console.println("r1 * 2 = " + (r1 * 2))
    Console.println("r1 / 2 = " + (r1 / 2))
  }
}
