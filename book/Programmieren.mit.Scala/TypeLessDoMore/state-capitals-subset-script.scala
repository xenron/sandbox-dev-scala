// code-examples/TypeLessDoMore/state-capitals-subset-script.scala

val stateCapitals = Map(
  "Alabama" -> "Montgomery",
  "Alaska"  -> "Juneau",
  // ...
  "Wyoming" -> "Cheyenne")
    
println( "Lese die Hauptstädte verpackt in Option-Objekten:" )
println( "Alabama: " + stateCapitals.get("Alabama") )
println( "Wyoming: " + stateCapitals.get("Wyoming") )
println( "Unknown: " + stateCapitals.get("Unknown") )

println( "Hole die Hauptstädt aus den Option-Objekten heraus:" )
println( "Alabama: " + stateCapitals.get("Alabama").get )
println( "Wyoming: " + stateCapitals.get("Wyoming").getOrElse("Nanu!") )
println( "Unknown: " + stateCapitals.get("Unknown").getOrElse("Nanu2!") )
