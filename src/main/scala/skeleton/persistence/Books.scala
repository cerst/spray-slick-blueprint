package skeleton.persistence

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.model.ForeignKeyAction.Cascade

case class Book(id: Option[Long], title: String, storeId: Long)

class Books(tag: Tag) extends Table[Book](tag, "books") {

  def id = column[Long]("id", O.AutoInc)

  def title = column[String]("title", O.DBType("VARCHAR(255)"), O.NotNull)

  def storeId = column[Long]("store_id", O.NotNull)

  def pk = primaryKey("books_id_pk", id)

  def fk_storeId = foreignKey("books_store_id_fk", storeId, Stores)(_.id, onDelete = Cascade)

  def * = (id.?, title, storeId) <>(Book.tupled, Book.unapply)

}

object Books extends TableQuery(new Books(_)) {
  def insertWithGenId(book: Book)(implicit session: Session): Long = Books returning Books.map(_.id) insert book

  val byStoreIdCompiled = Compiled {
    (storeId: Column[Long]) => Books filter (_.storeId === storeId)
  }
}
