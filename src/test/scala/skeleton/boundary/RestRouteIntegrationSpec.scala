package skeleton.boundary

import org.scalatest.{Matchers, FlatSpec}
import spray.http.StatusCodes.OK
import spray.testkit.ScalatestRouteTest
import skeleton.entity.Book

class RestRouteIntegrationSpec extends FlatSpec with Matchers with ScalatestRouteTest with RestRoute with RestRouteHookImpl {

  // we need to bring our json formats into scope
  import spray.httpx.SprayJsonSupport._
  import JsonProtocol._

  def actorRefFactory = system

  "The rest route" should "return all books of a collection via POST to /collections/1" in {
    Get("/collections/1") ~> route ~> check {
      status should equal(OK)

      // fails if type does not match
      entityAs[List[Book]]
    }
  }

}
