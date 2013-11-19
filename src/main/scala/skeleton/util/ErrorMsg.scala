package skeleton.util

sealed trait ErrorMsg {
  def content: String

  def is404: Boolean
}

case object _404 extends ErrorMsg {
  def content = throw new UnsupportedOperationException

  def is404 = true
}

case class _400(content: String) extends ErrorMsg {
  def is404 = false
}

object ErrorMsg {

  private def mkIdErrorMsg(objName: String, id: Long): String = "Could not find " + objName + " for given " + objName + "Id: " + id

  def notFound: ErrorMsg = _404

  def duplicateUserName(name: String) = _400("Name: " + name + " is already in use")

  def noCollectionForId(id: Long) = _400(mkIdErrorMsg("collection", id))

}
