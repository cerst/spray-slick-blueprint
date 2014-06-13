package skeleton.control

import scala.slick.driver.PostgresDriver.simple._
import skeleton.persistence.{Stores, Books, Book}
import skeleton.exception.PersistenceException
import skeleton.exception.PersistenceError.Validation
import scala.concurrent.Future

trait DbFacade {

  protected def database: Database

  @throws[PersistenceException]
  def insertBook(book: Book): Long = database withSession {
    implicit session =>
      if (Stores.notExistsByIdCompiled(book.storeId).run)
        throw new PersistenceException(Validation, "No collection found for collectionId: " + book.storeId)
      else
        Books insertWithGenId book
  }

  def findBooksByStore(storeId: Long): Seq[Book] = database withSession {
    implicit session => Books.byStoreIdCompiled(storeId).run
  }


}