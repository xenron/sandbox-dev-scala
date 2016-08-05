import scala.io.Source

object AutoCleanup extends App {

  def autoCleanUp[T](f: Source)(handler: Source => T): T = {
    try { // 1
      handler(f) // 2
    } finally {
      println("Closing resource")
      f.close()
    }
  }

  val s1 = Source.fromFile("/home/atul/myScala/example.txt")
  val s2 = Source.fromFile("/home/atul/myScala/example.txt")

  autoCleanUp(s1) { h => 
    for (line <- h.getLines) { // 3
      println(line.reverse)
    }
  }

  autoCleanUp(s2) { h =>
    for (line <- h.getLines) { // 4
      println(line.toCharArray.length + ":" + line)
    } 
  }
}

