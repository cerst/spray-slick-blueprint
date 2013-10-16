package skeleton.boundary

import spray.http.StatusCodes.{GatewayTimeout, BadRequest, OK}
import spray.routing.{ValidationRejection, HttpService}
import skeleton.messages.{IdRsp, InsertReq, BooksReq}
import skeleton.entity.{Entity, Book}
import scala.concurrent.{Await, Future}
import scala.reflect.ClassTag
import scala.util.{Try, Failure, Success}
import java.util.concurrent.TimeUnit
import akka.util.Timeout

trait RestRoute extends HttpService {

  import spray.httpx.SprayJsonSupport._
  import JsonProtocol._

  implicit def executionContext = actorRefFactory.dispatcher

  def handleRestMsg[T](msg: Any)(implicit tag: ClassTag[T]): Future[T]

  val route =
    get {
      path("collections" / LongNumber) {
        collectionId => complete {
          (OK, handleRestMsg[List[Book]](BooksReq(collectionId)))
        }
      }
    } ~ post {
      path("books") {
        entity(as[Book]) {
          book => {
            ctx => handleRestMsg[Either[Long, String]](InsertReq(book)).onSuccess {
              case Left(id) => ctx.complete (IdRsp(id))
              case Right(error) => ctx.reject(ValidationRejection(error))
            }
          }
        }
      }
    }

}
