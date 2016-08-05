// code-examples/AdvOOP/overrides/member-namespace-wont-compile.scala
// NICHT KOMPILIERBAR

class IllegalMemberNameUse {
  def member(i: Int) = 2 * i
  val member = 2         // FEHLER
  object member {        // FEHLER
    def apply() = 2
  }
}