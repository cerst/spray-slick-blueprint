package skeleton.persistence

import scala.slick.driver.PostgresDriver.simple._

case class Collection(id: Option[Long], title: String)

class Collections(tag: Tag) extends Table[Collection](tag, "collections") {

  def id = column[Long]("id", O.AutoInc)

  def title = column[String]("title", O.DBType("VARCHAR(255)"), O.NotNull)

  def pk = primaryKey("collections_id_pk", id)

  def * = (id.?, title) <>(Collection.tupled, Collection.unapply)

}

object collections extends TableQuery(new Collections(_)) {
  val notExistsByIdCompiled = Compiled {
    (id: Column[Long]) => books.filter(_.id === id).exists
  }
}
