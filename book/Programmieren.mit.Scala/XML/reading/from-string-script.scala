// code-examples/XML/reading/from-string-script.scala

import scala.xml._

val someXMLInAString = """
<sammich>
  <bread>Weizen</bread>
  <meat>Salami</meat>
  <condiments>
    <condiment expired="true">Mayo</condiment>
    <condiment expired="false">Senf</condiment>
  </condiments>
</sammich>
"""

val someXML = XML.loadString(someXMLInAString)
assert(someXML.isInstanceOf[scala.xml.Elem])
println(someXML)