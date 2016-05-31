package dg.study.courseware.ch02



class CompanionObject(n: String) {
  
  private val name = n
  
  def this() {
    this("abc")
  }
  
  def show() = println(name)
  
  def showList() = println(name.toList)
  
  import CompanionObject._
  
  def getValue(key: Char) = dictionary.getOrElse(key, "0")
  
  def merge(content: List[Char], result: List[String]): List[String] = {
    
    content match {
      case Nil => result
      case head::tail => {
        print("head is "); println(head)          
        print("tail is "); println(tail)          
        val left = result :+ getValue(head)
        print("left is "); println(left)
        merge(tail, left) 
      }
    }
  }
  
  def translate(): List[String] = {
    
    val chars = name.toList
    
    merge(chars, List())
  }
}

object CompanionObject {
  
  private val dictionary = Map('a' -> "1", 'b' -> "2", 'c' -> "3")
  
  def apply(n: String) = {
    new CompanionObject(n)
  }
  
  def apply(i: Int) = {
    new CompanionObject()
  }
  
  def main(args: Array[String]) = {

    val obj = CompanionObject(1)
    obj.show()
    obj.showList()
    
    val l = obj.translate()
    println(l)
  }
}