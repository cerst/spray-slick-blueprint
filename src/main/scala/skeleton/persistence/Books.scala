package skeleton.persistence

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.model.ForeignKeyAction.Cascade

case class Book(id: Option[Long], title: String, collectionId: Long)

class Books(tag: Tag) extends Table[Book](tag, "books") {

  def id = column[Long]("id", O.AutoInc)

  def title = column[String]("title", O.DBType("VARCHAR(255)"), O.NotNull)

  def collectionId = column[Long]("collection_id", O.NotNull)

  def pk = primaryKey("books_id_pk", id)

  def fk_collectionId = foreignKey("books_collection_id_fk", collectionId, collections)(_.id, onDelete = Cascade)

  def * = (id.?, title, collectionId) <>(Book.tupled, Book.unapply)

}

object books extends TableQuery(new Books(_)) {
  def insertWithGenId(book: Book)(implicit session: Session): Long = books returning books.map(_.id) insert book

  val byCollectionIdCompiled = Compiled {
    (collectionId: Column[Long]) => books filter (_.collectionId === collectionId)
  }


}
