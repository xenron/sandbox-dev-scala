package dg.study.courseware.ch11

import java.net.InetSocketAddress
import akka.actor.{ActorRef, ActorSystem, ActorLogging, Props, ActorContext, Actor}
import akka.io.{Tcp, IO}
import akka.util.ByteString

object TcpClient extends App {
  val system = ActorSystem("clientSystem")
  val endpoint = new InetSocketAddress("127.0.0.1", 11111)
  val clientListener = system.actorOf(ClientListener.props, "echo-listener")
  val clientRef = system.actorOf(Client.props(endpoint, clientListener), "echo-client")
  
  while (true) {
    print("Enter message:")
    val msg = readLine()
    
    if (msg == "close")
      clientRef ! msg
    else
      clientRef ! ByteString.fromString(msg)
  }
}

object ClientListener {
  def props = Props(new ClientListener)
}

class ClientListener extends Actor with ActorLogging {
  var cnt = 0
  
  import Tcp.Connected

  def receive = {
    case c @ Connected(remote, local) =>
      log.info("Client connected to {} from {}", remote, local)
    case data: String =>
      log.info("Event received: {}", data)
    case data: ByteString =>
      log.info("Message received: {}", data.decodeString("US-ASCII"))
  }

}
object Client {
  def props(remote: InetSocketAddress, replies: ActorRef) =
    Props(classOf[Client], remote, replies)
}

class Client(remote: InetSocketAddress, listener: ActorRef) extends Actor {
  import Tcp._
  import context.system
  
  IO(Tcp) ! Connect(remote)
  
  def receive = {
    case CommandFailed(_: Connect) =>
      listener ! "connect failed"
      context stop self
    case c @ Connected(remote, local) =>
      listener ! c
      val connection = sender()
      connection ! Register(self)
      context become {
        case data: ByteString =>
          connection ! Write(data)
        case CommandFailed(w: Write) =>
          // O/S buffer was full
          listener ! "write failed"
        case Received(data) =>
          listener ! data
        case "close" =>
          connection ! Close
          context stop self
      } 
  }
}
