// code-examples/FP/partial/isdefinedat-script.scala

val pantsTest: PartialFunction[String, String] = {
  case "Shorts" => "Ja, wir führen Shorts!"
}

println(pantsTest.isDefinedAt("Shorts"))
println(pantsTest.isDefinedAt("Skort"))
