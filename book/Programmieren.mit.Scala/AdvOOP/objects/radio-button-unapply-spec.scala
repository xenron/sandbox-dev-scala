// code-examples/AdvOOP/objects/radio-button-unapply-spec.scala

package objects
import org.specs._

object RadioButtonUnapplySpec extends Specification {
  "RadioButton.unapply" should {
    "should match a RadioButton object" in {
      val b = new RadioButton(true, "klick mich")
      b match {
        case RadioButton(on, label) => label mustEqual "klick mich"
        case _ => fail()
      }
    }
    "not match a Button (parent class) object" in {
      val b = new Button("klick mich")
      b match {
        case RadioButton(on, label) => fail()
        case x => x must notBeNull
      }
    }
    "not match a non-RadioButton object" in {
      val tf = new TextField("Hallo Welt!")
      tf match {
        case RadioButton(on, label) => fail()
        case x => x must notBeNull
      }
    }
    "extract the RadioButton's on/off state and label" in {
      val b = new RadioButton(true, "klick mich")
      b match {
        case RadioButton(on, label) => {
          label mustEqual "klick mich"
          on    mustEqual true
        }
        case _ => fail()
      }
    }
  }
}