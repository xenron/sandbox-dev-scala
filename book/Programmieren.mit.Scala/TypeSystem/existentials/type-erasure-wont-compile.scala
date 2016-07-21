// code-examples/TypeSystem/existentials/type-erasure-wont-work.scala
// WARNUNG: Funktiniert nicht so, wie sie es vielleicht erwarten.

object ProcessList {
  def apply[B](list: List[B]) = list match {
    case lInt:    List[Int]    => // tu etwas
    case lDouble: List[Double] => // tu etwas
    case lString: List[String] => // tu etwas
    case _                     => // Standardverhalten
  }
}