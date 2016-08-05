// code-examples/XML/reading/for-loop-script.scala

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

for (condiment <- (someXML \\ "condiment")) {
  if ((condiment \ "@expired").text == "true")
    println("Die Zutat " + condiment.text + " ist abgelaufen!")
}
