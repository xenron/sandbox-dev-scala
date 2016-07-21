// code-examples/TypeLessDoMore/import-example2-script.scala

def writeAboutBigInteger() = {

  import java.math.BigInteger.{
    ONE => _,
    TEN,     
    ZERO => JAVAZERO }

  // ONE ist im Endeffekt undefiniert
  // println( "ONE: "+ONE )
  println( "TEN: "+TEN )
  println( "ZERO: "+JAVAZERO )
}

writeAboutBigInteger()
