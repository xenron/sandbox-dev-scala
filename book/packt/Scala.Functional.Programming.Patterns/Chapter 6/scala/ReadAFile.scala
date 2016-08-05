
object ReadAFile extends App {
  import scala.io.Source

  val bufferedSource = Source.fromFile("example.txt")
  for (line <- bufferedSource.getLines) {
    println(line.toUpperCase)
  }
	Thread.sleep(100000)
  bufferedSource.close
}

