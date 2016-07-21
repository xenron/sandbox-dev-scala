object Sieve extends App {
  def f(s: Stream[Int], head: Int) = { // 1
  val r = s filter {
      x => {
        if (x % head != 0) {
          println(s"${x} is not evenly divisible by ${head}")
          true  // 2
        } else {
          println(s"${x} is evenly divisible by ${head}")
          println(s"Discarding ${x}")
          false // 3
        }
      }
    }
    r  // 4
  }

  def numStream(n: Int): Stream[Int] = {
    Stream.from(n)
  }

  def sieve(stream: Stream[Int]): Stream[Int] =
    stream.head #:: sieve(f(stream.tail, stream.head)) // 5

  val p = sieve(numStream(2))

  (p take 5) foreach { // driver
    println(_)
  }
}

