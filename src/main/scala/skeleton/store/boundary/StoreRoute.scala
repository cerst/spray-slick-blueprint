package skeleton.store.boundary

import spray.http.StatusCodes.{Created, BadRequest, NotFound, OK}
import spray.routing.HttpService
import scala.concurrent.Future
import scala.reflect.ClassTag
import skeleton.util.{ErrorMsg, IdRsp}
import skeleton.persistence.Book
import skeleton.store.control.BooksDbFacadeActor.{Insert, FindBooksBy}

trait StoreRoute extends HttpService {

  import spray.httpx.SprayJsonSupport._
  import JsonProtocol._

  implicit def executionContext = actorRefFactory.dispatcher

  def handleRestMsg[T](msg: Any)(implicit tag: ClassTag[T]): Future[T]

  val route =
    get {
      path("collections" / LongNumber) {
        collectionId => complete {
          (OK, handleRestMsg[List[Book]](FindBooksBy(collectionId)))
        }
      }
    } ~ post {
      path("books") {
        entity(as[Book]) {
          book => {
            ctx => handleRestMsg[Either[Long, ErrorMsg]](Insert(book)).onSuccess {
              case Left(id) => ctx.complete(Created, IdRsp(id))
              case Right(error) => if (error.is404) ctx complete NotFound else ctx complete(BadRequest, error.content)
            }
          }
        }
      }
    }

}
