// code-examples/FP/datastructs/map-script.scala

import scala.collection.immutable.ListMap

val stateCapitals = ListMap(
  "Alabama" -> "Montgomery",
  "Alaska"  -> "Juneau",
  "Wyoming" -> "Cheyenne")

val lengths:ListMap[String,Int] = stateCapitals map { kv => (kv._1, kv._2.length) }
println(lengths)
