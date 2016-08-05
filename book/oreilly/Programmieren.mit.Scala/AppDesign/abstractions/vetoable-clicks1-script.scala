// code-examples/AppDesign/abstractions/vetoable-clicks1-script.scala

trait Clickable {
  def click()
}

class Widget
class Button(val label: String) extends Widget with Clickable {
  def click() = println("Klick!")
}

trait VetoableClicks extends Clickable {
  val maxAllowed = 1
  private var count = 0
  abstract override def click() = {
    if (count < maxAllowed) {
      count += 1
      super.click()
    }
  }
}

val button1 = new Button("Klick mich!")
println("new Button(...)")
for (i <- 1 to 3 ) button1.click()

val button2 = new Button("Klick mich!") with VetoableClicks
println("new Button(...) with VetoableClicks")
for (i <- 1 to 3 ) button2.click()
