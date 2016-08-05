// code-examples/AdvOOP/objects/button-unapply-spec.scala

package objects
import org.specs._

object ButtonUnapplySpec extends Specification {
  "Button.unapply" should {
    "match a Button object" in {
      val b = new Button("klick mich")
      b match {
        case Button(label) => label mustEqual "klick mich"
        case _ => fail()
      }
    }
    "match a RadioButton object" in {
      val b = new RadioButton(false, "klick mich")
      b match {
        case Button(label) => label mustEqual "klick mich"
        case _ => fail()
      }
    }
    "not match a non-Button object" in {
      val tf = new TextField("Hallo Welt!")
      tf match {
        case Button(label) => fail()
        case x => x must notBeNull // Hack, damit Specs diesen Test nicht ignoriert.
      }
    }
    "extract the Button's label" in {
      val b = new Button("klick mich")
      b match {
        case Button(label) => label mustEqual "klick mich"
        case _ => fail()
      }
    }
    "extract the RadioButton's label" in {
      val rb = new RadioButton(false, "klick mich auch")
      rb match {
        case Button(label) => label mustEqual "klick mich auch"
        case _ => fail()
      }
    }
  }
}