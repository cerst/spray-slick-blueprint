package skeleton.control

import akka.actor.{Props, Actor}
import skeleton.control.DbFacadeActor.{Created, Insert, FindBooksBy}
import skeleton.persistence.{DbProvider, Book}
import akka.pattern.pipe
import scala.concurrent.Future

class DbFacadeActor extends Actor with DbFacade {

  val database = DbProvider.get

  import context.dispatcher

  def receive = {
    case FindBooksBy(collectionId) => Future(findBooksByStore(collectionId)) pipeTo sender
    case Insert(book) => Future(insertBook(book)) map Created pipeTo sender
  }

}

object DbFacadeActor {
  def props = Props[DbFacadeActor]

  case class Created(id: Long)

  case class Insert(book: Book)

  case class FindBooksBy(collectionId: Long)


}
