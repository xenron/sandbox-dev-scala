package dg.study.hw.ch07

import scala.collection.immutable.ListMap

class PersonInfo {
  var people: Map[String, Int] = Map();
  def sort : Map[String, Int] = {
    ListMap(people.toList.sortBy{_._2}:_*)
  }
}

class TeacherInfo extends PersonInfo{
  def addTeacher(name: String, age: Int) = {
    people += (name -> age)
  }
}

class StudentInfo extends PersonInfo{
  def addStudent(name: String, score: Int) = {
    people += (name -> score)
  }
}

object MainClass {
  def main(args: Array[String]) {
    val studentInfo = new StudentInfo;
    studentInfo.addStudent("tom", 100)
    studentInfo.addStudent("jerry", 8)
    studentInfo.addStudent("lucy", 61)
    studentInfo.addStudent("peter", 17)
    println("------------- StudentInfo -----------------")
    println(studentInfo.people)
    println(studentInfo.sort)
    val teacherInfo = new TeacherInfo;
    teacherInfo.addTeacher("tom", 35)
    teacherInfo.addTeacher("jerry", 40)
    teacherInfo.addTeacher("lucy", 9)
    teacherInfo.addTeacher("peter", 27)
    println("-------------- TeacherInfo ----------------")
    println(teacherInfo.people)
    println(teacherInfo.sort)
    println("-------------- End ----------------")
  }
}