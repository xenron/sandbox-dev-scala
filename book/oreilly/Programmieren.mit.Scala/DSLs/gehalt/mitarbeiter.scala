// code-examples/DSLs/gehalt/mitarbeiter.scala

package gehalt

case class Name(vor: String, zu: String)

case class Mitarbeiter(name: Name, jährlichesBruttoGehalt: Betrag)