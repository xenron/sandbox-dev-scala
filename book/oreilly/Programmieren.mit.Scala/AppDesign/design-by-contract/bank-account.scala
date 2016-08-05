// code-examples/AppDesign/design-by-contract/bank-account.scala

class BankAccount(val balance: Double) {
  require(balance >= 0.0)
  def debit(amount: Double) = {
    require(amount > 0.0, "Der Sollbetrag muss > 0.0 sein.")
    assume(balance - amount > 0.0, "Überziehungen sind nicht gestattet.")
    new BankAccount(balance - amount)
  }
  def credit(amount: Double) = {
    require(amount > 0.0, "Der Habenbetrag muss > 0.0 sein.")
    new BankAccount(balance + amount)
  }
}
