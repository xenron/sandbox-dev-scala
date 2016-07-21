import scala.util.parsing.combinator.RegexParsers

class AssignmentParser extends RegexParsers {
  case class AssignExpr(lhs: String, rhs: String) {
    override def toString = lhs + " was assigned " + rhs
  }

  def lhs: Parser[String]   = """[a-z]+""".r       ^^ { _.toString }
  def num: Parser[Int]    = """(0|[1-9]\d*)""".r ^^ { _.toInt }
  def str: Parser[String]   = """[a-z]+""".r       ^^ { _.toString }
  def eq: Parser[String] = """\s*=\s*""".r ^^ { _.toString.trim }
  def assignNum: Parser[AssignExpr] = lhs ~ eq ~ num ^^ { case lhs ~ eq ~ number => AssignExpr(lhs, number.toString()) }
  def assignStr: Parser[AssignExpr] = lhs ~ eq ~ str ^^ { case lhs ~ eq ~ str => AssignExpr(lhs, str) }
}

object TestAssignmentParser extends AssignmentParser {
  def tryParsing() = {
    List(parse(assignStr, "x    =           ppp"), parse(assignNum, "y     =   4")).foreach(_ match {
      case Success(matched,_) => println(matched)
      case Failure(msg,_) => println("FAILURE: " + msg)
      case Error(msg,_) => println("ERROR: " + msg)
    })

    parse(assignNum, "y = 4") match {
      case Success(matched,_) => println(matched)
      case Failure(msg,_) => println("FAILURE: " + msg)
      case Error(msg,_) => println("ERROR: " + msg)
    }

  }
}

object AnAssignmentParser extends App {
  TestAssignmentParser.tryParsing()
}
