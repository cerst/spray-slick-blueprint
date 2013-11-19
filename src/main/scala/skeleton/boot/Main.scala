package skeleton.boot

import akka.actor.{Props, ActorSystem}
import spray.can.Http
import akka.io.IO
import skeleton.persistence.DbProvider

object Main extends App {

  implicit val system = ActorSystem("THE-SYSTEM")

  val service = system.actorOf(Props[DispatchRouteActor], "dispatch-route")

  args.find(_ == "--resetDb") match {
    case Some(s) => DbProvider init true
    case None => DbProvider init false
  }

  IO(Http) ! Http.Bind(service, "localhost", port = 8080)

}
