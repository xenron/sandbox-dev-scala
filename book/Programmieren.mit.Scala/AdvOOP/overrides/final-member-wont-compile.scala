// code-examples/AdvOOP/overrides/final-member-wont-compile.scala
// NICHT KOMPILIERBAR

class NotFixed {
  final def fixedMethod = "fixiert"
}

class Changeable2 extends NotFixed {
  override def fixedMethod = "nicht fixiert"   // FEHLER
}
