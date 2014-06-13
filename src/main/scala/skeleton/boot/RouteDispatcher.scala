package skeleton.boot

import spray.routing.{RequestContext, HttpService}
import akka.actor.Actor
import skeleton.control.DbFacadeActor
import skeleton.boundary.RouteActor

trait DispatchRoute extends HttpService {

  def handleBooksReq(rc: RequestContext): Unit

  private[boot] val route = {
    pathPrefix("api") {
      handleBooksReq
    }
  }
}

class DispatchRouteActor extends Actor with DispatchRoute {

  def actorRefFactory = context

  def receive = runRoute(route)

  val dbFacade = context actorOf(DbFacadeActor.props, "dbFacade")

  val routeActor = context actorOf(RouteActor props dbFacade, "route")

  def handleBooksReq(rc: RequestContext): Unit = routeActor ! rc
}
