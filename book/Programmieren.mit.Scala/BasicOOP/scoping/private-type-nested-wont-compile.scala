// code-examples/BasicOOP/scoping/private-type-nested-wont-compile.scala
// NICHT KOMPILIERBAR

package scopeA {
  class PrivateClass1 {
    class Nested {
      private[PrivateClass1] val nestedField = 1
    }

    private[PrivateClass1] val nested = new Nested
    val nestedNested = nested.nestedField
  }

  class PrivateClass2 extends PrivateClass1 {
    val nField = new Nested().nestedField   // FEHLER
  }

  class PrivateClass3 {
    val privateClass1 = new PrivateClass1
    val privateNField = privateClass1.nested.nestedField // FEHLER
  }
}
