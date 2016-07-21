// code-examples/FP/datastructs/foldleft-map-script.scala

val r=List(1, 2, 3, 4, 5, 6).foldLeft(List[String]()) {
  (list, x) => ("<" + x + ">") :: list
}.reverse
println(r)