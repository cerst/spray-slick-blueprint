package skeleton.util

import spray.util.LoggingContext
import spray.http.StatusCode
import spray.http.StatusCodes.{BadRequest, NotFound}
import spray.routing.{HttpService, ExceptionHandler}
import skeleton.exception.PersistenceError
import java.util.concurrent.TimeUnit.SECONDS
import skeleton.exception.PersistenceException

/**
 * Created by gerstbec on 05.06.2014.
 */
trait RouteUtil {
  this: HttpService =>

  implicit val timeout = akka.util.Timeout(2, SECONDS)

  implicit def exceptionHandler(implicit log: LoggingContext) = {
    def mapToCode(error: PersistenceError): StatusCode = error match {
      case PersistenceError.NotFound => NotFound
      case _ => BadRequest
    }

    ExceptionHandler {
      case PersistenceException(error, msg) => complete(mapToCode(error), msg)
    }
  }
}
