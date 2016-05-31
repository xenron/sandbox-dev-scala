package dg.study.courseware.ch04



object Arguments {
  
  def variableArg(a: String *) = {
    
    a.foreach { x => println(x) }
  }
  
  def printPerson(name: String, age: Int = 10) {
    
    printf("name is %s, age is %d\n", name, age)
  }
  
  def sum(a: Int, b: Int, c: Int) = {
    a + b + c
  }
  
  def main(args: Array[String]): Unit = {
    
    variableArg("1", "2", "3", "4");
    val k = Array("10", "20", "30", "40", "50")
    variableArg(k:_*)
    
    printPerson(name="john")
    
    val sum35 = sum(3, _: Int, 5)
    
    println(sum35(4))
  }
}