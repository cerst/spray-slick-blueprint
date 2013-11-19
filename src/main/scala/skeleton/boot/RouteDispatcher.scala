package skeleton.boot

import spray.routing.{RequestContext, HttpService}
import akka.actor.Actor
import skeleton.books.control.BooksDbFacadeActor
import skeleton.books.boundary.BooksRouteActor

trait DispatchRoute extends HttpService {

  def handleBooksReq(rc: RequestContext): Unit

  private[boot] val route = {
    pathPrefix("api") {
      pathPrefix("books") {
        handleBooksReq
      }
    }
  }
}

class DispatchRouteActor extends Actor with DispatchRoute {

  def actorRefFactory = context

  def receive = runRoute(route)

  val booksDbFacade = context actorOf(BooksDbFacadeActor.props, "books-dbFacade")

  val booksRoute = context actorOf(BooksRouteActor props booksDbFacade, "books-route")

  def handleBooksReq(rc: RequestContext): Unit = booksRoute ! rc
}
