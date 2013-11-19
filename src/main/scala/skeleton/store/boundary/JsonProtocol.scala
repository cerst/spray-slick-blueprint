package skeleton.store.boundary

import spray.json.DefaultJsonProtocol
import skeleton.store.entity.{IdRsp, Book}

object JsonProtocol extends DefaultJsonProtocol {

  implicit val bookFormat = jsonFormat3(Book)
  implicit val idRspFormat = jsonFormat1(IdRsp)

}
