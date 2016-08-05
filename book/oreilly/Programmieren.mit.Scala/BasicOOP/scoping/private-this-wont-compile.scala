// code-examples/BasicOOP/scoping/private-this-wont-compile.scala
// NICHT KOMPILIERBAR

package scopeA {
  class PrivateClass1(private[this] val privateField1: Int) {
    private[this] val privateField2 = 1

    def equalFields(other: PrivateClass1) =
      (privateField1 == other.privateField1) && // FEHLER
      (privateField2 == other.privateField2) &&
      (nested == other.nested)

    class Nested {
      private[this] val nestedField = 1
    }

    private[this] val nested = new Nested
  }

  class PrivateClass2 extends PrivateClass1(1) {
    val field1 = privateField1  // FEHLER
    val field2 = privateField2  // FEHLER
    val nField = new Nested().nestedField  // FEHLER
  }

  class PrivateClass3 {
    val privateClass1 = new PrivateClass1(1)
    val privateField1 = privateClass1.privateField1  // FEHLER
    val privateField2 = privateClass1.privateField2  // FEHLER
    val privateNField = privateClass1.nested.nestedField // FEHLER
  }
}
