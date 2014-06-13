package skeleton.boundary

import akka.actor.{Props, ActorRef, Actor}
import scala.reflect.ClassTag
import scala.concurrent.Future
import skeleton.util.RouteUtil
import akka.pattern.ask


class RouteActor(private val dbFacade: ActorRef) extends Actor with Route with RouteUtil {

  def actorRefFactory = context

  def receive = runRoute(route)

  def handleRestMsg[T](msg: Any)(implicit tag: ClassTag[T]): Future[T] = (dbFacade ? msg).mapTo[T]

}

object RouteActor {

  def props(dbFacade: ActorRef) = Props(classOf[RouteActor], dbFacade)
}
