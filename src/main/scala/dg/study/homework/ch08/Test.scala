package dg.study.hw.ch08

import java.util.concurrent.{Callable, FutureTask, Executors, ExecutorService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Success, Failure}

object Test {
  def main(args: Array[String]) {

    val studentInfo = new StudentInfo;
    val teacherInfo = new TeacherInfo;

    val t1 = Future {
      addTeacher(teacherInfo, "tom", 35)
    }
    val t2 = Future {
      addTeacher(teacherInfo, "jerry", 40)
    }
    val t3 = Future {
      addTeacher(teacherInfo, "lucy", 9)
    }
    val t4 = Future {
      addTeacher(teacherInfo, "peter", 27)
    }
    val s1 = Future {
      addStudent(studentInfo, "tom", 100)
    }
    val s2 = Future {
      addStudent(studentInfo, "jerry", 8)
    }
    val s3 = Future {
      addStudent(studentInfo, "lucy", 61)
    }
    val s4 = Future {
      addStudent(studentInfo, "peter", 17)
    }
    val future = Future.sequence(Seq(s1, s2, s3, s4, t1, t2, t3, t4))

    Await.result(future, Duration.fromNanos(0))

    future.onComplete({
      case Success(result) => println(result)
      case Failure(t) => println(s"Error: %{t.getMessage}")
    })
  }

  def addTeacher(teacher: TeacherInfo, name: String, age: Int) = {
    teacher.addTeacher(name, age)
    println("addTeacher: " + name + " --- " + age)
  }

  def addStudent(student: StudentInfo, name: String, score: Int) = {
    student.addStudent(name, score)
    println("addStudent: " + name + " --- " + score)
  }

}

class PersonInfo {
  var people: Map[String, Int] = Map();
}

class TeacherInfo extends PersonInfo {
  def addTeacher(name: String, age: Int) = {
    people += (name -> age)
  }
}

class StudentInfo extends PersonInfo {
  def addStudent(name: String, score: Int) = {
    people += (name -> score)
  }
}