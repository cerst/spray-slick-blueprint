package skeleton.boundary

import spray.json.DefaultJsonProtocol
import skeleton.persistence.Book
import skeleton.control.DbFacadeActor

object JsonProtocol extends DefaultJsonProtocol {

  implicit val bookFormat = jsonFormat3(Book)
  implicit val createdFormat = jsonFormat1(DbFacadeActor.Created)

}
