package skeleton.boundary

import spray.http.StatusCodes
import spray.routing.HttpService
import scala.concurrent.Future
import scala.reflect.ClassTag
import skeleton.control.DbFacadeActor.{Created, Insert, FindBooksBy}
import skeleton.persistence.Book

trait Route extends HttpService {

  import spray.httpx.SprayJsonSupport._
  import JsonProtocol._

  implicit def executionContext = actorRefFactory.dispatcher

  def handleRestMsg[T](msg: Any)(implicit tag: ClassTag[T]): Future[T]

  val route =
    get {
      path("stores" / LongNumber) {
        storeId => complete {
          (StatusCodes.OK, handleRestMsg[Seq[Book]](FindBooksBy(storeId)))
        }
      }
    } ~ post {
      path("books") {
        entity(as[Book]) {
          book => onSuccess(handleRestMsg[Created](Insert(book))) {
            complete(StatusCodes.Created, _)
          }
        }
      }
    }
}