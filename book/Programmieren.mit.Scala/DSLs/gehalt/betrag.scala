// code-examples/DSLs/gehalt/betrag.scala

package gehalt

import java.math.{BigDecimal => JBigDecimal,
    MathContext => JMathContext, RoundingMode => JRoundingMode}

/** Zwecks besserer Genauigkeit werden die meisten Berechnungen mit
 * JBigDecimals ausgeführt.
 */
class Betrag(val betrag: BigDecimal) {

  def + (m: Betrag)  =
      Betrag(betrag.bigDecimal.add(m.betrag.bigDecimal))
  def - (m: Betrag)  =
      Betrag(betrag.bigDecimal.subtract(m.betrag.bigDecimal))
  def * (m: Betrag)  =
      Betrag(betrag.bigDecimal.multiply(m.betrag.bigDecimal))
  def / (m: Betrag)  =
      Betrag(betrag.bigDecimal.divide(m.betrag.bigDecimal,
          Betrag.scale, Betrag.jroundingMode))

  def <  (m: Betrag)  = betrag <  m.betrag
  def <= (m: Betrag)  = betrag <= m.betrag
  def >  (m: Betrag)  = betrag >  m.betrag
  def >= (m: Betrag)  = betrag >= m.betrag

  override def equals (o: Any) = o match {
    case m: Betrag => betrag equals m.betrag
    case _ => false
  }

  override def hashCode = betrag.hashCode * 31

  // Hack: Die korrekte Konvertierung double2Double muss explizit aufgerufen
  // werden.
  override def toString =
      format("EUR %.2f", double2Double(betrag.doubleValue))
}

object Betrag {
  def apply(amount: BigDecimal)  = new Betrag(amount)
  def apply(amount: JBigDecimal) = new Betrag(scaled(new BigDecimal(amount)))
  def apply(amount: Double)      = new Betrag(scaled(BigDecimal(amount)))
  def apply(amount: Long)        = new Betrag(scaled(BigDecimal(amount)))
  def apply(amount: Int)         = new Betrag(scaled(BigDecimal(amount)))

  def unapply(m: Betrag) = Some(m.betrag)

  protected def scaled(d: BigDecimal) = d.setScale(scale, roundingMode)

  val scale = 4
  val jroundingMode = JRoundingMode.HALF_UP
  val roundingMode  = BigDecimal.RoundingMode./*ROUND_*/HALF_UP
  val context = new JMathContext(scale, jroundingMode)
}

object Type2Betrag {
  implicit def bigDecimal2Betrag(b: BigDecimal)   = Betrag(b)
  implicit def jBigDecimal2Betrag(b: JBigDecimal) = Betrag(b)
  implicit def double2Betrag(d: Double)           = Betrag(d)
  implicit def long2Betrag(l: Long)               = Betrag(l)
  implicit def int2Betrag(i: Int)                 = Betrag(i)
}
