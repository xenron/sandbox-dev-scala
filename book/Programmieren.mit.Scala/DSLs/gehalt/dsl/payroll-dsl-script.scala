// code-examples/DSLs/gehalt/dsl/payroll-dsl-script.scala
import gehalt._
import gehalt.dsl._
import gehalt.dsl.regeln._

val gehaltsrechner = regeln { mitarbeiter =>
  mitarbeiter gehalt_für 2.wochen minus_abzüge_für { brutto =>
    einkommensteuer              ist  (25.00  prozent_von brutto)
    krankenversicherungsbeiträge sind (500.00 in brutto.währung)
    rentenversicherungsbeiträge  sind (10.00  prozent_von brutto)
  }
}

val fritz = Mitarbeiter(Name("Fritz", "Tester"), Betrag(80000))
val elke = Mitarbeiter(Name("Elke", "Mustermann"), Betrag(90000))

List(fritz, elke).foreach { mitarbeiter =>
  val gehalt = gehaltsrechner(mitarbeiter)
  println(format("%s %s: %s\n", mitarbeiter.name.vor, mitarbeiter.name.zu, gehalt))
}
