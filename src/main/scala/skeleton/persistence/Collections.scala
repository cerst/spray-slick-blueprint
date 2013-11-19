package skeleton.persistence

import scala.slick.driver.PostgresDriver.simple._
import skeleton.store.entity.Collection


object Collections extends Table[Collection]("collections") {

  def id = column[Long]("id", O.AutoInc)

  def title = column[String]("title", O.DBType("VARCHAR(255)"), O.NotNull)

  def pk = primaryKey("collections_id_pk", id)

  def * = id.? ~ title <>(Collection, Collection.unapply _)

  def notExistsFor(id: Long)(implicit session: Session): Boolean = !existsForId(id).firstOption.getOrElse(false)

  protected val existsForId = for {
    id <- Parameters[Long]
    c <- Collections where (_.id === id)
  } yield c.exists

}
