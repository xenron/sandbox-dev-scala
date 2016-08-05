// code-examples/XML/reading/pattern-matching-script.scala

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

someXML match {
  case <sammich>{ingredients @ _*}</sammich> => {
    for (cond @ <condiments>{_*}</condiments> <- ingredients)
      println("Zutaten: " + cond.text)
  }
}