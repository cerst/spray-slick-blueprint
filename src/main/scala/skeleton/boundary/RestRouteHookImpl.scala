package skeleton.boundary

import akka.actor.{Props, ActorRefFactory}
import akka.pattern.ask
import skeleton.control.DbFacadeActor
import scala.concurrent.Future
import akka.util.Timeout
import scala.reflect.ClassTag
import java.util.concurrent.TimeUnit.SECONDS

trait RestRouteHookImpl {

  implicit val timeout = Timeout(2, SECONDS)

  def actorRefFactory: ActorRefFactory

  val dbFacade = actorRefFactory.actorOf(Props[DbFacadeActor], "db-facade")

  def handleRestMsg[T](msg: Any)(implicit tag: ClassTag[T]) : Future[T] = {
    (dbFacade ? msg).mapTo[T]
  }

}
