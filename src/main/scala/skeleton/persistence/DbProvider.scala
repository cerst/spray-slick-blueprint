package skeleton.persistence

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.jdbc.StaticQuery
import Database.threadLocalSession
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
    DbProvider.get withSession {
      if (reset)
        StaticQuery updateNA "drop schema public cascade;create schema public;" execute()
      try {
        // simple check: access a table: if an exception occurs we assume that this table and all other do not exist
        (Books where (_.id === 0L)).firstOption
      } catch {
        case e: Exception => {
          val ddls = Collections.ddl ++ Books.ddl
          ddls.create
        }
      }
    }
  }

  def shutdown(): Unit = {
    dataSource close()
  }

}
