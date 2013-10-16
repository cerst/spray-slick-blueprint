package skeleton.util

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.jdbc.StaticQuery
import skeleton.entity.{Books, Collections}
import Database.threadLocalSession
import com.typesafe.config.ConfigFactory

object DbProvider {

  val conf = ConfigFactory load()

  private val database = {
    val dbConf = conf getConfig "database"
    def read(s: String): String = dbConf getString s
    Database forURL(read("url"), driver = read("driver"), user = read("user"), password = read("password"))
  }

  def get = database

  def init(reset: Boolean): Unit = {
    DbProvider.get withSession {
      if (reset)
        StaticQuery.updateNA("drop schema public cascade;create schema public;")
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

}
