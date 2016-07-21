// code-examples/AppDesign/abstractions/encoded-string-script.scala

import encodedstring._
import encodedstring.EncodedString._

def p(s: EncodedString) = {
  println("EncodedString: " + s)
  s.toTokens foreach (x => println("token: " + x))
}

val csv = EncodedString("Scala,ist,super!", Separator.COMMA)
val tsv = EncodedString("Scala\tist\tsuper!", Separator.TAB)

p(csv)
p(tsv)

println( "\nExtraktion:" )
List(csv, "ProgrammierenMitScala", tsv, 3.14159) foreach { 
  case EncodedString(str, delim) => 
    println( "EncodedString: \"" + str + "\", Trennzeichen: \"" + delim + "\"" )
  case s: String => println( "String: " + s )
  case x => println( "Unbekannter Wert: " + x )
}