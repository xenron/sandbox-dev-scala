// code-examples/AppDesign/annotations/specialized-script.scala

import java.util.Date

class SpecialType[@specialized(Int,Long) +T](val wert:T)

val a1 = new SpecialType[Int](10)
println(a1)
val a2 = new SpecialType[Long](10L)
println(a2)
val a3 = new SpecialType[Byte](10)
println(a3)
val a4 = new SpecialType[Short](10)
println(a4)
val a5 = new SpecialType[Float](10)
println(a5)
val a6 = new SpecialType[Double](10)
println(a6)
val a7 = new SpecialType[Char](10)
println(a7)

val a8 = new SpecialType[String]("Hallo")
println(a8)

val a9 = new SpecialType[Date](new Date)
println(a9)
