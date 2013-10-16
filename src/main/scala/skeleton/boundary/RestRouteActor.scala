package skeleton.boundary

import akka.actor.{Actor, ActorLogging}

class RestRouteActor extends Actor with ActorLogging with RestRoute with RestRouteHookImpl {

  def actorRefFactory = context

  def receive = runRoute(route)

}
