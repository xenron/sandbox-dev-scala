package dg.study.hw.ch05

object StudentInfo {
  var students: Map[String, Int] = Map();

  def addStudent(name: String, grade: Int) = {
    students += (name -> grade)
  }

  def delStudent(name: String) = {
    students -= name
  }

  def modifyStudent(name: String, grade: Int) = {
    students += (name -> grade)
  }

  def getGradeStudents(grade: Int): Map[String, Int] = {
    //    var resultMap : Map[String, Int] = Map();
    return students.filter(_._2 == grade)
  }

  def main(args: Array[String]) {
    addStudent("tom", 1)
    addStudent("jerry", 2)
    addStudent("lucy", 1)
    addStudent("peter", 3)
    println(students)
    modifyStudent("peter", 2)
    delStudent("lucy")
    println(students)
    println(getGradeStudents(1))
    println(getGradeStudents(2))
  }
}
