// code-examples/FP/datastructs/foldreduce-script.scala

val r1 = List(1,2,3,4,5,6) reduceLeft(_ + _)
println(r1)

val r2 = List(1,2,3,4,5,6).foldLeft(10)(_ * _)
println(r2)
