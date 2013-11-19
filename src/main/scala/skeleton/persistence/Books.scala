package skeleton.persistence

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.ForeignKeyAction.Cascade
import skeleton.util.ErrorMsg
import skeleton.store.entity.Book

object Books extends Table[Book]("store") {

  def id = column[Long]("id", O.AutoInc, O.PrimaryKey)

  def title = column[String]("title", O.DBType("VARCHAR(255)"), O.NotNull)

  def collectionId = column[Long]("collection_id", O.NotNull)

  def pk = primaryKey("books_id_pk", id)

  def fk_collectionId = foreignKey("books_collection_id_fk", collectionId, Collections)(_.id, onDelete = Cascade)

  def * = id.? ~ title ~ collectionId <>(Book, Book.unapply _)

  // shim to make auto inc work with postgres
  def withGenId = (title ~ collectionId) <>( {
    (t, c) => Book(None, t, c)
  }, {
    (b: Book) => Some(b.title, b.collectionId)
  }
    )

  def insertWithGenId(b: Book)(implicit session: Session): Either[Long, ErrorMsg] = {
    if (Collections notExistsFor b.collectionId)
      Right(ErrorMsg noCollectionForId b.collectionId)
    else
      Left(Books.withGenId insert b)
  }

  def findFor(collectionId: Long)(implicit session: Session): List[Book] = forCollectionId(collectionId) list()

  private val forCollectionId = for {
    collectionId <- Parameters[Long]
    b <- Books where (_.collectionId === collectionId)
  } yield b

}
