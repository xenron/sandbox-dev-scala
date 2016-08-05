// code-examples/DSLs/gehalt/dsl/duration.scala

package gehalt.dsl

case class Dauer(val anzahl: Int) {
  /** @return Zahl der Arbeitstage in 'anzahl' Wochen. */
  def wochen = anzahl * 5

  /** @return Zahl der Arbeitstage in 'anzahl' Jahren. */
  def jahre = anzahl * 260
}
