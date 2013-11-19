package skeleton.store.boundary

import akka.actor.{Props, ActorRef, Actor}
import scala.reflect.ClassTag
import scala.concurrent.Future
import skeleton.util.Timeout
import akka.pattern.ask

class StoreRouteActor(private val booksDbFacade: ActorRef) extends Actor with StoreRoute with Timeout{

  def actorRefFactory = context

  def receive = runRoute(route)

  def handleRestMsg[T](msg: Any)(implicit tag: ClassTag[T]): Future[T] = (booksDbFacade ? msg).mapTo[T]
}

object StoreRouteActor {

  def props(booksDbFacade: ActorRef) = Props(classOf[StoreRouteActor], booksDbFacade)
}
