// code-examples/Traits/ui/button-callbacks.scala

package ui

class ButtonWithCallbacks(val label: String, 
    val clickedCallbacks: List[() => Unit]) extends Widget {

  require(clickedCallbacks != null, "Callback-Liste darf nicht null sein!")

  def this(label: String, clickedCallback: () => Unit) =
    this(label, List(clickedCallback))

  def this(label: String) = {
    this(label, Nil)
    println("Warnung. Button hat keine Klick-Callbacks!")
  }

  def click() = {
    // ... Logik für die grafische Darstellung des Button-Klicks ...
    clickedCallbacks.foreach(f => f())
  }
}