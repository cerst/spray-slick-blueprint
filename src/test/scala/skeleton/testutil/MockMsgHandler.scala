package skeleton.testutil

import scala.reflect.ClassTag
import scala.concurrent.Future

trait MockMsgHandler {

  def handleMsg[T](msg: Any)(implicit tag: ClassTag[T]): Future[T]
}
