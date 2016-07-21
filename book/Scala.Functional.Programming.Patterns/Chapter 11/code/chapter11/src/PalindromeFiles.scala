import java.io.{FileNotFoundException, File}

import scala.util.{Failure, Success, Try}
import scala.io.Source._

object PalindromeFiles extends App {
  def checkFileExt(f: File, ext: String) = Try {
    // 1
    if (f.getName.endsWith(ext))
      f
    else
      throw new RuntimeException("Wrong extension")
  }

  def fileIsPalindrome(f: File) = Try {
    // 2
    val lines = fromFile(f).mkString.stripLineEnd
    if (lines == lines.reverse)
      f
    else
      throw new RuntimeException("Not a palindrome")
  }

  def checkFileIfItIsAPalindrome(f: File) = checkFileExt(f, ".txt").flatMap(fileIsPalindrome(_))

  val checkOne = checkFileIfItIsAPalindrome(
    new File("./two.txt")) // 3
  val checkTwo = checkFileIfItIsAPalindrome(
    new File("" +
      "./abrakakarba.txt")) // 4
  val checkThree = checkFileIfItIsAPalindrome(
      new File("./fileDoesNotExist.txt")) // 5
  checkOne.foreach(println)
  checkTwo.foreach(println)

  def printResult(x: Try[File]) = x match {
    case Success(f) => println(f) // 1
    case Failure(e) => println(e) // 2
  }

  printResult(checkOne)
  printResult(checkTwo)
  printResult(checkThree)

}
