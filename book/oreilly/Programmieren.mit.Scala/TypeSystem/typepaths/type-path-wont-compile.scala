// code-examples/TypeSystem/typepaths/type-path-wont-compile.scala
// FEHLER: Nicht kompilierbar

trait Service {
  trait Logger {
    def log(message: String): Unit
  }
  val logger: Logger
  
  def run = {
    logger.log("Starte " + getClass.getSimpleName + ":")
    doRun
  }
  
  protected def doRun: Boolean
}

object MyService1 extends Service {
  class MyService1Logger extends Logger {
    def log(message: String) = println("1: "+message)
  }
  override val logger = new MyService1Logger
  def doRun = true  // führe etwas Richtiges aus
}

object MyService2 extends Service {
  override val logger = MyService1.logger  // FEHLER
  def doRun = true  // führe etwas Richtiges aus
}
