// code-examples/FP/implicits/implicit-conversion-script.scala
import scala.collection.immutable.StringOps

class FancyString(val str: String)

object FancyString2StringOps {
    implicit def fancyString2RichString(fs: FancyString) =
        new StringOps(fs.str)
}

import FancyString2StringOps._

val fs = new FancyString("scala")
println(fs.capitalize.reverse)