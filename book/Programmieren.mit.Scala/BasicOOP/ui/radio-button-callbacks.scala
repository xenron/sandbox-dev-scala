// code-examples/BasicOOP/ui/radio-button-callbacks.scala

package ui

/**
 * Button mit zwei Zuständen: ein oder aus, wie
 * altmodische Kanal-Auswahltasten an einem Radio.
 */
class RadioButtonWithCallbacks(
  var on: Boolean, label: String, clickedCallbacks: List[() => Unit])
      extends ButtonWithCallbacks(label, clickedCallbacks) {

  def this(on: Boolean, label: String, clickedCallback: () => Unit) =
      this(on, label, List(clickedCallback))

  def this(on: Boolean, label: String) = this(on, label, Nil)
}
