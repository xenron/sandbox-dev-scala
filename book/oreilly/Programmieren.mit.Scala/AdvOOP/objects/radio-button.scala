// code-examples/AdvOOP/objects/radio-button.scala

package objects

/**
 * Button mit zwei Zuständen, ein oder aus, wie ein altmodischer
 * Kanalwahl-Knopf an einem Radio.
 */
class RadioButton(val on: Boolean, label: String) extends Button(label)

object RadioButton {
  def unapply(button: RadioButton) = Some((button.on, button.label))
                    // entspricht: = Some(Pair(button.on, button.label))
}
