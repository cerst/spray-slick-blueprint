package skeleton.boot

import spray.routing.{RequestContext, HttpService}
import akka.actor.Actor
import skeleton.store.control.BooksDbFacadeActor
import skeleton.store.boundary.StoreRouteActor

trait DispatchRoute extends HttpService {

  def handleBooksReq(rc: RequestContext): Unit

  private[boot] val route = {
    pathPrefix("api") {
      pathPrefix("store") {
        handleBooksReq
      }
    }
  }
}

class DispatchRouteActor extends Actor with DispatchRoute {

  def actorRefFactory = context

  def receive = runRoute(route)

  val booksDbFacade = context actorOf(BooksDbFacadeActor.props, "store-dbFacade")

  val storeRoute = context actorOf(StoreRouteActor props booksDbFacade, "store-route")

  def handleBooksReq(rc: RequestContext): Unit = storeRoute ! rc
}
