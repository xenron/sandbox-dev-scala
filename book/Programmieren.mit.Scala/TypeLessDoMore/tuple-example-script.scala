// code-examples/TypeLessDoMore/tuple-example-script.scala

def tupleator(x1: Any, x2: Any, x3: Any) = (x1, x2, x3)

val t = tupleator("Hallo", 1, 2.3)
println( "Das ganze Tupel: " + t )
println( "Das erste Element des Tupels: " + t._1 )
println( "Das zweite Element des Tupels: " + t._2 )
println( "Das dritte Element des Tupels: " + t._3 )

val (t1, t2, t3) = tupleator("Welt", '!', 0x22)
println( t1 + " " + t2 + " " + t3 )
