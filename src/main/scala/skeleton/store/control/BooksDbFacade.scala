package skeleton.store.control

import scala.slick.driver.PostgresDriver.simple._
import Database.threadLocalSession
import skeleton.persistence.{Books, DbProvider}
import skeleton.store.entity._
import skeleton.util.ErrorMsg

trait BooksDbFacade {

  protected val database = DbProvider.get

  def insertEntity(ir: InsertReq): Either[Long, ErrorMsg] = ir.entity match {
    case b: Book => database withSession (Books insertWithGenId b)
    case x: Any => throw new IllegalArgumentException("unsupported entity type: " + x)
  }

  def findBooksForCollection(booksReq: BooksReq): List[Book] = database withSession (Books findFor booksReq.collectionId)

}