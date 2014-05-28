package skeleton.store.control

import akka.actor.{Props, Actor}
import skeleton.persistence.Book
import skeleton.store.control.BooksDbFacadeActor.{Insert, FindBooksBy}

class BooksDbFacadeActor extends Actor with BooksDbFacade {

  def receive = {
    case FindBooksBy(collectionId) => sender ! findBooksByCollection(collectionId)
    case Insert(book) => sender ! insertBook(book)
  }

}

object BooksDbFacadeActor {
  def props = Props[BooksDbFacadeActor]

  case class Insert(book: Book)

  case class FindBooksBy(collectionId: Long)

}
