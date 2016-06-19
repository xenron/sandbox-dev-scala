package dg.study.hw.ch11

import akka.actor._
import akka.io.{IO, Tcp}
import akka.util.ByteString
import java.net._

object TcpServer extends App {
  
  val system = ActorSystem("echo-service-system")
  val endpoint = new InetSocketAddress("localhost", 11111)
  system.actorOf(EchoService.props(endpoint), "echo-service")

  readLine(s"Hit ENTER to exit ...${System.getProperty("line.separator")}")
  system.shutdown()
}

object EchoService {
  def props(endpoint: InetSocketAddress): Props =
    Props(new EchoService(endpoint))
}

class EchoService(endpoint: InetSocketAddress) extends Actor with ActorLogging {
  import context.system
  import Tcp.Bound
  import Tcp.Connected
  import Tcp.Register
  
  IO(Tcp) ! Tcp.Bind(self, endpoint)
  
  override def receive = {
    case b @ Bound(address) =>
      log.info("bound on address: {}", address)
      
    case c @ Connected(remote, local) =>
      val handler = context.actorOf(SimpleHandler.props)
      val connection = sender()
      connection ! Register(handler, keepOpenOnPeerClosed = true)
      log.info("remote {} connected at {}", remote, local)
  }
}

object SimpleHandler {
  def props : Props = Props(new SimpleHandler)
}

class SimpleHandler extends Actor with ActorLogging {
  
  import Tcp.PeerClosed
  import Tcp.Received
  import Tcp.Write

  val teacherInfo = new TeacherInfo;
  val studentInfo = new StudentInfo;
  
  override def receive = {
    case Received(data) =>

      val receiveData = data.decodeString("US-ASCII")
      val arr = receiveData.toString.split(",")
      var responseData = ""
      if("add".equals(arr(0))){
        if("teacher".equals(arr(1))){
          teacherInfo.addTeacher(arr(2),arr(3).toInt)
        } else if("student".equals(arr(1))){
          studentInfo.addStudent(arr(2),arr(3).toInt)
        }
        responseData = "add success"
      } else if("del".equals(arr(0))){
        if("teacher".equals(arr(1))){
          teacherInfo.delTeacher(arr(2))
        } else if("student".equals(arr(1))){
          studentInfo.delStudent(arr(2))
        }
        responseData = "del success"
      } else if("search".equals(arr(0))){
        if("teacher".equals(arr(1))){
          responseData = teacherInfo.people.filter((e) => e._1.equals(arr(2))).toString()
        } else if("student".equals(arr(1))){
          responseData = studentInfo.people.filter((e) => e._1.equals(arr(2))).toString()
        }
        responseData = "search success: " + responseData
      }
      sender() ! Write(ByteString.fromString(responseData))

      val content = data.decodeString("US-ASCII")
      println("data received: " + content)
      log.info("data: {}", content)
      
    case PeerClosed => context stop self
  }
}

class PersonInfo extends Serializable {
  var people: Map[String, Int] = Map();
}

class TeacherInfo extends PersonInfo {
  def addTeacher(name: String, age: Int) = {
    people += (name -> age)
  }
  def delTeacher(name: String) = {
    people -= (name)
  }
}

class StudentInfo extends PersonInfo {
  def addStudent(name: String, score: Int) = {
    people += (name -> score)
  }
  def delStudent(name: String) = {
    people -= (name)
  }
}
