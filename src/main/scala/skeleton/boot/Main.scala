package skeleton.boot

import akka.actor.{Props, ActorSystem}
import spray.can.Http
import akka.io.IO
import skeleton.boundary.RestRouteActor
import skeleton.util.DbProvider

object Main extends App {

  implicit val system = ActorSystem("THE-SYSTEM")

  val service = system.actorOf(Props[RestRouteActor], "route")

  args.find(_ == "resetDb") match {
    case Some(s) => DbProvider init true
    case None => DbProvider init false
  }

  IO(Http) ! Http.Bind(service, "localhost", port = 8080)

}
