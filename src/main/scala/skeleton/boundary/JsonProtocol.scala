package skeleton.boundary

import spray.json.DefaultJsonProtocol
import skeleton.entity.Book
import skeleton.messages.IdRsp

object JsonProtocol extends DefaultJsonProtocol {

  implicit val bookFormat = jsonFormat3(Book)
  implicit val idRspFormat = jsonFormat1(IdRsp)

}
