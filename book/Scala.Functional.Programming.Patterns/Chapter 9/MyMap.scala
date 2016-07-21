import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.ListBuffer

object MyMap extends App {
  def myMap[X, Y, Container[X] <: Traversable[X]](collection: Container[X])(f: X ⇒ Y)(
    implicit builderSpec: CanBuildFrom[Container[Y], Y, Container[Y]]): Container[Y] = {
    val buildIt = builderSpec()
    collection.foreach { x =>
      buildIt += f(x)
    }
    buildIt.result
  }

  println(myMap(Seq(1,2)) { _ >  1 })
  println(myMap(List(1,2)) { _ < 1 })
  println(myMap(Vector(7, 8, 9)) { _ * 2 })
  println(myMap(ListBuffer(11, 12, 13)) { _ + 1 })
  println(myMap(Set(11, 12, 13)) { _ + 1 })
}

