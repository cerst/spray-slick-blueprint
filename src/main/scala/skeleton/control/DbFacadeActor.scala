package skeleton.control

import akka.actor.{Actor, ActorLogging}
import skeleton.messages.{InsertReq, BooksReq}

class DbFacadeActor extends Actor with ActorLogging with DbFacade {

  def receive = {
    case b: BooksReq => sender ! findBooksForCollection(b)
    case i: InsertReq => {
      log.error("RECEIVED: " + i)
      sender ! insertEntity(i)
    }
    case x: Any => log.error("received unsupported message: " + x)
  }

}
