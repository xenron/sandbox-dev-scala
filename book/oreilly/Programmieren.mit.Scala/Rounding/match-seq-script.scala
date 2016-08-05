// code-examples/Rounding/match-seq-script.scala

val willWork = List(1, 3, 23, 90)
val willNotWork = List(4, 18, 52)
val empty = List()

for (l <- List(willWork, willNotWork, empty)) {
  l match {
    case List(_, 3, _, _) => println("Vier Elemente, von denen das zweite '3' ist.")
    case List(_*) => println("Eine andere Liste mit 0 oder mehre Elementen.")
  }
}
