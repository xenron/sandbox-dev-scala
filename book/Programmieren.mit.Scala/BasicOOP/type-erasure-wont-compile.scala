// code-examples/BasicOOP/type-erasure-wont-compile.scala
// NICHT KOMPILIERBAR

object Foo {
  def bar(list: List[String]) = list.toString
  def bar(list: List[Int]) = list.size.toString
}