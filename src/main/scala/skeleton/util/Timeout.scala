package skeleton.util

import java.util.concurrent.TimeUnit.SECONDS

trait Timeout {

  implicit val timeout = akka.util.Timeout(2, SECONDS)

}
