// code-examples/AdvOOP/overrides/final-class-wont-compile.scala
// NICHT KOMPILIERBAR

final class Fixed {
  def doSomething = "Hier geschieht etwas!"
}

class Changeable1 extends Fixed     // FEHLER
