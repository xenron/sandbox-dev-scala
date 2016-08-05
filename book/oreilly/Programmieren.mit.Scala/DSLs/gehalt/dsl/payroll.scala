// code-examples/DSLs/gehalt/dsl/payroll.scala

package gehalt.dsl
import gehalt._

object regeln {
  def apply(regeln: Mitarbeiter => Gehaltsabrechnung) = new GehaltsabrechnungBuilderRegeln(regeln)

  implicit def int2Dauer(i: Int) = Dauer(i)

  implicit def mitarbeiter2BruttoZahlungBuilder(e: Mitarbeiter) =
    new BruttoZahlungBuilder(e)

  implicit def bruttoZahlungBuilder2AbzügeBuilder(b: BruttoZahlungBuilder)
    = new AbzügeBuilder(b)

  implicit def double2AbzügeBuilderAbzugHelper(d: Double) =
    new AbzügeBuilderAbzugHelper(d)
}

import regeln._

class GehaltException(message: String, cause: Throwable)
  extends RuntimeException(message, cause)

protected[dsl] class GehaltsabrechnungBuilderRegeln(regeln: Mitarbeiter => Gehaltsabrechnung) {
  def apply(mitarbeiter: Mitarbeiter) = {
    try {
      regeln(mitarbeiter)
    } catch {
      case th: Throwable => new GehaltException(
        "Fehler bei der Berechnung für Mitarbeiter: " + mitarbeiter, th
      )
    }
  }
}

import Type2Betrag._

protected[dsl] class BruttoZahlungBuilder(val mitarbeiter: Mitarbeiter) {
  var brutto: Betrag = 0

  def gehalt_für(tage: Int) = {
    brutto += täglicherBruttoVerdienst(mitarbeiter.jährlichesBruttoGehalt) * tage
    this
  }

  // Annahme: 260 Arbeitstage = 52 Wochen (einschl. Urlaub) * 5 Tage/Woche.
  def wöchentlicherBruttoVerdienst(jährlich: Betrag) = jährlich / 52.0
  def täglicherBruttoVerdienst(jährlich: Betrag)  = jährlich / 260.0
}

protected[dsl] class AbzügeBuilder(bzb: BruttoZahlungBuilder) {
  val mitarbeiter = bzb.mitarbeiter
  var gehaltsabrechnung: Gehaltsabrechnung  = new Gehaltsabrechnung (bzb.brutto, bzb.brutto, 0)

  def währung = this

  def minus_abzüge_für(abzügeRegeln: AbzügeBuilder => Unit) = {
    abzügeRegeln(this)
    gehaltsabrechnung
  }

  def addAbzüge(betrag: Betrag) =
    gehaltsabrechnung = gehaltsabrechnung plusAbzüge betrag

  def addAbzügeProzentualVomBrutto(prozentsatz: Double) = {
    val betrag = gehaltsabrechnung.brutto * (prozentsatz/100.)
    addAbzüge(betrag)
  }
}

class AbzügeRechner {
  def ist(builder: AbzügeBuilder) = apply(builder)
  def sind(builder: AbzügeBuilder) = apply(builder)

  def apply(builder: AbzügeBuilder) = {}
}

object einkommensteuer extends AbzügeRechner
object krankenversicherungsbeiträge extends AbzügeRechner
object rentenversicherungsbeiträge extends AbzügeRechner

protected[dsl] class AbzügeBuilderAbzugHelper(val faktor: Double) {
  def in (builder: AbzügeBuilder) = {
    builder addAbzüge Betrag(faktor)
    builder
  }
  def prozent_von (builder: AbzügeBuilder) = {
    builder addAbzügeProzentualVomBrutto faktor
    builder
  }
}
