package skeleton.store.control

import akka.actor.{Props, Actor, ActorLogging}
import skeleton.store.entity.{BooksReq, InsertReq}

class BooksDbFacadeActor extends Actor with ActorLogging with BooksDbFacade {

  def receive = {
    case b: BooksReq => sender ! findBooksForCollection(b)
    case i: InsertReq => sender ! insertEntity(i)
    case x: Any => log.error("received unsupported message: " + x)
  }

}

object BooksDbFacadeActor {
  def props = Props[BooksDbFacadeActor]
}
