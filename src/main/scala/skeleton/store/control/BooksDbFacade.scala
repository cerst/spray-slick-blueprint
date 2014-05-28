package skeleton.store.control

import skeleton.persistence.{Book, books, DbProvider}
import scala.slick.driver.PostgresDriver.simple._

trait BooksDbFacade {

  protected val database = DbProvider.get

  def insertBook(book: Book): Long = database withSession {
    //TODO: check for collection
    implicit session => books insertWithGenId book
  }

  def findBooksByCollection(collectionId: Long): Seq[Book] = database withSession {
    implicit session => books.byCollectionIdCompiled(collectionId).run
  }

}