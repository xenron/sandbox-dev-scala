// code-examples/XML/reading/inline-script.scala

import scala.xml._

val someXML =
<sammich>
  <bread>Weizen</bread>
  <meat>Salami</meat>
  <condiments>
    <condiment expired="true">Mayo</condiment>
    <condiment expired="false">Senf</condiment>
  </condiments>
</sammich>

assert(someXML.isInstanceOf[scala.xml.Elem])
println(someXML)
