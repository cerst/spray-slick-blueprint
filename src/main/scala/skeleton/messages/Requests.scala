package skeleton.messages

import skeleton.entity.Entity

case class BooksReq(collectionId: Long)

case class InsertReq(entity: Entity)