// code-examples/AdvOOP/ui3/button.scala

package ui3

class Button(val label: String) extends Widget with Clickable {
    
  def click() = {
    // Logik zur Darstellung eines Knopfdrucks...
  }
  
  def draw() = {
    // Logik zum Zeichnen des Buttons in einem Fenster, einer Webseite usw.
  }
  
  override def toString() = 
    "(button: label=" + label + ", " + super.toString() + ")"
}