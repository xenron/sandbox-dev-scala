package dg.study.hw.ch09

import java.io.{ObjectOutputStream, FileOutputStream}

import akka.actor.Actor
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.actor.Props

case class AkkaMessage(message: Any)
case class Response(response: Any)

class Server extends Actor {
  val teacherInfo = new TeacherInfo;
  val studentInfo = new StudentInfo;
  val out = new ObjectOutputStream(new FileOutputStream("d:/tmp/test.obj"))

  override def receive: Receive = {
    case msg: AkkaMessage => {
      println("服务端收到消息: " + msg.message)
      val receiveMessage = msg.message
      val arr = receiveMessage.toString.split(",")
      if("teacher".equals(arr(0))){
        teacherInfo.addTeacher(arr(1),arr(2).toInt)
      } else if("student".equals(arr(0))){
        studentInfo.addStudent(arr(1),arr(2).toInt)
      }
      out.writeObject(teacherInfo)
      sender ! Response("response_" + msg.message)
    }
    case _ => println("服务端不支持的消息类型 .. ")
  }
}

object Server {
  //创建远程Actor:ServerSystem
  def main(args: Array[String]): Unit = {
    val serverSystem = ActorSystem("test", ConfigFactory.parseString("""
      akka {
       actor {
          provider = "akka.remote.RemoteActorRefProvider"
        }
        remote {
          enabled-transports = ["akka.remote.netty.tcp"]
          netty.tcp {
            hostname = "127.0.0.1"
            port = 2555
          }
        }
      }
                                                                     """))

    serverSystem.actorOf(Props[Server], "server")

  }
}

class PersonInfo extends Serializable {
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