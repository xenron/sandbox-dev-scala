// code-examples/DSLs/payroll/paycheck.scala

package payroll

/** Wir ignorieren ungültige (?) Fälle wie negative Nettogehälter,
 *  wenn die Abzüge das Bruttogehalt übersteigen.
 */
case class Paycheck(gross: Money, net: Money, deductions: Money) {

  def plusGross (m: Money)      = Paycheck(gross + m, net + m, deductions)
  def plusDeductions (m: Money) = Paycheck(gross,     net - m, deductions + m)
}

