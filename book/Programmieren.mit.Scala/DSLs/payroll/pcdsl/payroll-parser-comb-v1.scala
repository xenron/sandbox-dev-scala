// code-examples/DSLs/payroll/pcdsl/payroll-parser-comb-v1.scala

package payroll.pcdsl
import scala.util.parsing.combinator._
import org.specs._ 
import payroll._
import payroll.Type2Money._

class PayrollParserCombinatorsV1 extends JavaTokenParsers {

  def paycheck = empl ~ gross ~ deduct

  def empl = "Gehaltsabrechnung" ~> "für" ~> "Mitarbeiter" ~> employeeName

  def gross = "ist" ~> "Gehalt" ~> "für" ~> duration

  def deduct = "minus" ~> "Abzüge" ~> "für" ~> "{" ~> deductItems  <~ "}"

  // stringLiteral ist in JavaTokenParsers definiert
  def employeeName = stringLiteral

  // decimalNumber ist in JavaTokenParsers definiert
  def duration = decimalNumber ~ weeksDays

  def weeksDays = "Wochen" | "Woche" | "Tage" | "Tag"

  def deductItems = repsep(deductItem, "," )

  def deductItem = deductKind ~> deductAmount

  def deductKind = tax | insurance | retirement

  def tax = "Einkommensteuer"

  def insurance = "Krankenversicherung" ~> "Beiträge"

  def retirement = "Rentenversicherung" ~> "Beiträge"

  def deductAmount = percentage | amount

  def percentage = toBe ~> doubleNumber <~ "Prozent" <~ "von" <~ "Brutto"

  def amount = toBe ~> doubleNumber <~ "in" <~ "Brutto" <~ "Währung"

  def toBe = "ist" | "sind"

  // floatingPointNumber ist in JavaTokenParsers definiert
  def doubleNumber = floatingPointNumber
}
