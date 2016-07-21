// code-examples/AdvOOP/objects/button.scala

package objects
import ui3.Clickable

class Button(val label: String) extends Widget with Clickable {
  
  def click() = {
    // Logik zur Darstellung eines Knopfdrucks...
  }

  def draw() = {
    // Logik zum Zeichnen des Knopfs auf dem Bildschirm, der Webseite usw.
  }
  
  override def toString() = "(button: label="+label+", "+super.toString()+")"
}

object Button {
  def unapply(button: Button) = Some(button.label)
}