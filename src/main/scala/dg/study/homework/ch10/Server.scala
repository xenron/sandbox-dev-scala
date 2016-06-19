package dg.study.hw.ch10

import java.io.{FileOutputStream, ObjectOutputStream}

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

case class AkkaMessage(message: Any)
case class Response(response: Any)

class Server extends Actor with ActorLogging {
  println("Constructor")
  val teacherInfo = new TeacherInfo;
  val studentInfo = new StudentInfo;
  val out = new ObjectOutputStream(new FileOutputStream("d:/tmp/test.obj"))

  override def preStart() = println("pre-start")

  override def postStop() = println("post-stop")

  // 重新启动策略
  override def preRestart(reason: Throwable, msg: Option[Any]) = {
    println("pre-restart")
    println("reason: " + reason.getMessage)

    msg match {
      case Some(content) => println("msg: " + content)
      case None => println("no msg")
    }

    super.preRestart(reason, msg)
  }

  override def postRestart(reason: Throwable) = {
    println("post-restart")
    super.postRestart(reason)
  }

  override def receive: Receive = {
    case msg: AkkaMessage => {
      println("服务端收到消息: " + msg.message)
      val receiveMessage = msg.message

      if("restart".equals(receiveMessage)) {
        println("restart received")
        throw new IllegalStateException("forced restart")
      }

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