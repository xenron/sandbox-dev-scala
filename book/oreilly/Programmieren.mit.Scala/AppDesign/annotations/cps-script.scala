// code-examples/AppDesign/annotations/cps-script.scala

import scala.util.continuations._

def foo(): Int @cps[Int] = { // could leave out return type
  shift { k: (Int=>Int) =>
    k(7)
  } + 1
}
reset(2 * foo()) // result: 16