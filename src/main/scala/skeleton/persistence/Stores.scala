package skeleton.persistence

import scala.slick.driver.PostgresDriver.simple._

/**
 * Created by gerstbec on 05.06.2014.
 */
case class Store(id: Option[Long], name: String)

class Stores(tag: Tag) extends Table[Store](tag, "stores") {
  def id = column[Long]("id", O.AutoInc, O.PrimaryKey)

  def name = column[String]("name", O.DBType("VARCHAR(100)"))

  def * = (id.?, name) <>(Store.tupled, Store.unapply)
}

object Stores extends TableQuery(new Stores(_)) {

  val notExistsByIdCompiled = Compiled {
    storeId: Column[Long] => !Stores.filter(_.id === storeId).exists
  }
}
