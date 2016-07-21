// code-examples/DSLs/payroll/api/payroll-api-script.scala

import payroll.api._
import payroll.api.DeductionsCalculator._
import payroll._
import payroll.Type2Money._

val buck = Employee(Name("Fritz", "Tester"), Money(80000))
val jane = Employee(Name("Elke", "Mustermann"), Money(90000))

List(buck, jane).foreach { employee =>
  // Annahme: Das Jahr hat 52 Wochen.
  val biweeklyGross = employee.annualGrossSalary / 26.

  val deductions = federalIncomeTax(employee, biweeklyGross) +
          stateIncomeTax(employee, biweeklyGross) +
          insurancePremiums(employee, biweeklyGross) +
          retirementFundContributions(employee, biweeklyGross)

  val check = Paycheck(biweeklyGross, biweeklyGross - deductions, deductions)

  println(format("%s %s: %s\n", employee.name.first, employee.name.last, check))
}
