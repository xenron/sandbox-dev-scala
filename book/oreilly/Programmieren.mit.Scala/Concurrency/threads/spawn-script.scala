// code-examples/Concurrency/threads/spawn-script.scala

import scala.concurrent.ops._

object SpawnExample {
  def main(args: Array[String]) {
    println("Dies läuft synchron.")

    spawn {
      println("Dies läuft asynchron.")
    }
  }
}

