// code-examples/BasicOOP/scoping/private-wont-compile.scala
// NICHT KOMPILIERBAR

package scopeA {
  class PrivateClass1(private val privateField1: Int) {
    private val privateField2 = 1

    def equalFields(other: PrivateClass1) =
      (privateField1 == other.privateField1) &&
      (privateField2 == other.privateField2) &&
      (nested == other.nested)

    class Nested {
      private val nestedField = 1
    }

    private val nested = new Nested
  }

  class PrivateClass2 extends PrivateClass1(1) {
    val field1 = privateField1  // FEHLER
    val field2 = privateField2  // FEHLER
    val nField = new Nested().nestedField // FEHLER
  }

  class PrivateClass3 {
    val privateClass1 = new PrivateClass1(1)
    val privateField1 = privateClass1.privateField1 // FEHLER
    val privateField2 = privateClass1.privateField2 // FEHLER
    val privateNField = privateClass1.nested.nestedField // FEHLER
  }

  private class PrivateClass4

  class PrivateClass5 extends PrivateClass4  // FEHLER
  protected class PrivateClass6 extends PrivateClass4 // FEHLER
  private class PrivateClass7 extends PrivateClass4
}

package scopeB {
  class PrivateClass4B extends scopeA.PrivateClass4  // FEHLER
}