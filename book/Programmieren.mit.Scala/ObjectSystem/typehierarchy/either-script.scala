// code-examples/ObjectSystem/typehierarchy/either-script.scala

def exceptionToLeft[T](f: => T): Either[java.lang.Throwable, T] = try {
  Right(f)
} catch {
  case ex => Left(ex)
}

def throwsOnOddInt(i: Int) = i % 2 match {
  case 0 => i
  case 1 => throw new RuntimeException(i + " ist ungerade!")
}

for(i <- 0 to 3)
  exceptionToLeft(throwsOnOddInt(i)) match {
    case Left(ex) => println("Oh, wir erhalten die Exception " + ex.toString)
    case Right(x) => println(x)
}