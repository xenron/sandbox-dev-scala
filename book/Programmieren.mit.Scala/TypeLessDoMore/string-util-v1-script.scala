// code-examples/TypeLessDoMore/string-util-v1-script.scala
// Version 1 von "StringUtil".</code

object StringUtil {
  def joiner(strings: List[String], separator: String): String = 
    strings.mkString(separator)

  def joiner(strings: List[String]): String = joiner(strings, " ")
}
import StringUtil._  // Importiert die joiner-Methoden.

println( joiner(List("Programmieren","mit","Scala")) )