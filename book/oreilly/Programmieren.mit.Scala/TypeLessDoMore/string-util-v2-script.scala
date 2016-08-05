// code-examples/TypeLessDoMore/string-util-v2-script.scala

object StringUtil {
  def joiner(strings: List[String], separator: String = " "): String =
    strings.mkString(separator)
}
import StringUtil._  // Joiner-Methoden importieren

println(joiner(List("Programmieren", "mit", "Scala")))