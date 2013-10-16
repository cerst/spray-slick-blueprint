package skeleton.control

import scala.slick.driver.PostgresDriver.simple._
import Database.threadLocalSession
import skeleton.entity.{Books, Book}
import skeleton.util.DbProvider
import skeleton.messages.{InsertReq, BooksReq}

trait DbFacade {

  protected val database = DbProvider.get

  def insertEntity(ir: InsertReq): Either[Long, String] = ir.entity match {
    case b: Book => database withSession (Books insertWithGenId b)
    case x: Any => throw new IllegalArgumentException("unsupported entity type: " + x)
  }

  def findBooksForCollection(booksReq: BooksReq): List[Book] = database withSession (Books findFor booksReq.collectionId)

}
