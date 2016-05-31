package dg.study.courseware.ch09

import akka.actor._
import akka.event._

object MainApp extends App {
  
  val system = ActorSystem("ticketingSystem");
  val boxOffice = system.actorOf(BoxOffice.props, "office")
  println("box office created")

  boxOffice ! BoxOffice.Supply("madamButterfly", 10)
  boxOffice ! BoxOffice.Request("madamButterfly", 3)
  boxOffice ! BoxOffice.Request("madamBufferfly", 12)
}

object TicketSeller {
  def props(event: String) = Props(new TicketSeller(event))
  case class Add(numberOfTickets: Int) 
  case class Buy(tickets: Int) 
  case class Tickets(count: Int) 
}

class TicketSeller(val event: String) extends Actor {
  import TicketSeller._
  
  var tickets: Int = 0
  val log = Logging(context.system, this)

  def receive = {
    case Add(newTickets) => {
      tickets = tickets + newTickets
      log.info("added several tickets, ticket count for " + event + " now: " + tickets)
    }

    case Buy(nrOfTickets) => 
      if (tickets < nrOfTickets) {
        sender() ! BoxOffice.SoldOut(this.event)
        log.info("tickets for " + event + " sold out.")
      }
      else {
        tickets = tickets - nrOfTickets
        sender() ! Tickets(nrOfTickets)
        log.info("tickets for " + event + " now at: " + tickets)
      }
  }
}

object BoxOffice {
  def props = Props(new BoxOffice)
  
  case class SoldOut(event: String)
  case class Request(event: String, tickets: Int)
  case class Supply(event: String, tickets: Int)
}

class BoxOffice extends Actor {
  
  val log = Logging(context.system, this)
  var sellers = Map[String, ActorRef]()
  
  def createTicketSeller(event: String) = {
    if (sellers.contains(event)) {
      sellers(event)
    }
    else {
      val seller = context.actorOf(TicketSeller.props(event), event)
      sellers += event -> seller
      seller
    }
  }
  
  def receive = {
    case req: BoxOffice.Request => {
      val seller = createTicketSeller(req.event)
      seller ! TicketSeller.Buy(req.tickets)
    }
    
    case supply: BoxOffice.Supply => {
      val seller = createTicketSeller(supply.event)
      seller ! TicketSeller.Add(supply.tickets)
    }
    
    case soldOut: BoxOffice.SoldOut => {
      println("tickets for " + soldOut.event + " sold out")
    }
  }
    
}
