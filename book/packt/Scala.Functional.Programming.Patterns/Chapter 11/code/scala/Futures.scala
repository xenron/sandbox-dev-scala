import scala.concurrent.{ExecutionContext, Future, Promise, Await}
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

object Futures extends App {
  def from(n: Int): Stream[Int] = {
	Thread.sleep(2000)
    n #:: from(n + 1)
  }

  val printNumbers = Future {
    val start = from(10).take(4)
    start.foldLeft(0) { _ + _ }
   }

  val f = printNumbers map {
    case 46 => {
      println("Got it")
    }
    case _ => println("Huh?")
  }

  Await.ready(f, 60 seconds)
}

