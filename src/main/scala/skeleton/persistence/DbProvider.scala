package skeleton.persistence

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.jdbc.StaticQuery
import com.typesafe.config.ConfigFactory
import com.mchange.v2.c3p0.ComboPooledDataSource

object DbProvider {

  val conf = ConfigFactory load()

  private val (dataSource, database) = {
    val dbConf = conf getConfig "database"
    def read(s: String): String = dbConf getString s
    val ds = new ComboPooledDataSource
    ds setJdbcUrl read("url")
    ds setDriverClass read("driver")
    ds setUser read("user")
    ds setPassword read("password")
    (ds, Database forDataSource ds)
  }

  def get = database

  def init(reset: Boolean): Unit = {
    database withSession {
      implicit session =>
        if (reset)
          StaticQuery updateNA "drop schema public cascade;create schema public;" execute()
        try {
          // simple check: access a table: if an exception occurs we assume that this table and all other do not exist
          Stores.notExistsByIdCompiled(1).run
        } catch {
          case e: Exception =>
            val ddls = Stores.ddl ++ Books.ddl
            ddls.create
            Stores.insert(Store(Some(1), "Store 1"))
        }
    }
  }

  def shutdown(): Unit = {
    dataSource close()
  }

}
