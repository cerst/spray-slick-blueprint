package skeleton.store.entity

trait Entity

case class Book(id: Option[Long], title: String, collectionId: Long) extends Entity

case class Collection(id: Option[Long], title: String) extends Entity
