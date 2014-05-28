package skeleton.store.boundary

import spray.json.DefaultJsonProtocol
import skeleton.persistence.Book
import skeleton.util.IdRsp

object JsonProtocol extends DefaultJsonProtocol {

  implicit val bookFormat = jsonFormat3(Book)
  implicit val idRspFormat = jsonFormat1(IdRsp)

}
