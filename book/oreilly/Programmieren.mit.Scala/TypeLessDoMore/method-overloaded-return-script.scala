// code-examples/TypeLessDoMore/method-overloaded-return-script.scala
// Version 1 von "StringUtil" (mit Kompilierfehler).
// FEHLERL Lässt sich nicht kompilieren - erfordert Rückgebetyp String
// für jede zweite "joiner"-Methode.

object StringUtil {
  def joiner(strings: List[String], separator: String): String = 
    strings.mkString(separator)

  def joiner(strings: List[String]) = joiner(strings, " ")   // FEHLER
}
import StringUtil._  // Importiert die joiner-Methoden

println( joiner(List("Programmieren", "mit", "Scala")) )