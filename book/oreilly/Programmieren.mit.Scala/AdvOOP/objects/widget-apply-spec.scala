// code-examples/AdvOOP/objects/widget-apply-spec.scala

package objects
import org.specs._

object WidgetApplySpec extends Specification {
  "Widget.apply with a valid widget specification string" should {
    "return a widget instance with the correct fields set" in {
      Widget("(button: label=klick mich, (Widget))") match {
        case Some(w) => w match {
          case b:Button => b.label mustEqual "klick mich"
          case x => fail(x.toString())
        }
        case None => fail("None zurückgegeben.")
      }
      Widget("(textfield: text=Dies ist ein Text, (Widget))") match {
        case Some(w) => w match {
          case tf:TextField => tf.text mustEqual "Dies ist ein Text"
          case x => fail(x.toString())
        }
        case None => fail("None zurückgegeben.")
      }
    }
  }
  "Widget.apply with an invalid specification string" should {
    "return None" in {
      Widget("(button: , (Widget)") mustEqual None
    }
  }
}