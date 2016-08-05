// code-examples/DSLs/gehalt/gehaltsabrechnung.scala

package gehalt

/** Wir ignorieren ungültige (?) Fälle wie negative Nettogehälter,
 *  wenn die Abzäge das Bruttogehalt äbersteigen.
 */
case class Gehaltsabrechnung(brutto: Betrag, netto: Betrag, abzüge: Betrag) {

  def plusBrutto (m: Betrag) = Gehaltsabrechnung(brutto + m, netto + m, abzüge)
  def plusAbzüge (m: Betrag) = Gehaltsabrechnung(brutto,     netto - m, abzüge + m)
}

