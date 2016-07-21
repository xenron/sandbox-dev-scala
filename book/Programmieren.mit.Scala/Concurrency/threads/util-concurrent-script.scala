// code-examples/Concurrency/threads/util-concurrent-script.scala

import java.util.concurrent._

class ThreadIdentifier extends Runnable {
  def run {
    println("Hallo von Thread " + currentThread.getId)
  }
}

val pool = Executors.newFixedThreadPool(5)

for (i <- 1 to 10) {
  pool.execute(new ThreadIdentifier)
}