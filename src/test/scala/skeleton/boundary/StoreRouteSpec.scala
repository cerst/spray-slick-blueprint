package skeleton.boundary

import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}
import spray.testkit.ScalatestRouteTest
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import scala.reflect.ClassTag
import scala.concurrent.Future
import akka.actor.ActorRefFactory
import skeleton.testutil.MockMsgHandler
import spray.http.StatusCodes.{Created, OK}
import skeleton.persistence.Book
import skeleton.control.DbFacadeActor.{FindBooksBy, Insert}
import skeleton.control.DbFacadeActor

class StoreRouteSpec extends FlatSpec with Matchers with BeforeAndAfter with MockitoSugar with ScalatestRouteTest {

  var mockMsgHandler: MockMsgHandler = _

  val testRoute = new Route {
    implicit def actorRefFactory: ActorRefFactory = system

    def handleRestMsg[T](msg: Any)(implicit tag: ClassTag[T]): Future[T] = mockMsgHandler.handleMsg[T](msg)
  }

  def route = testRoute.route

  before {
    mockMsgHandler = mock[MockMsgHandler]
  }

  import spray.httpx.SprayJsonSupport._
  import JsonProtocol._

  "The store route" should "allow a user to create a new book via POST to /books" in {
    val newBook = Book(None, "test-book", 1)
    val newBookId = 1L
    when(mockMsgHandler.handleMsg[DbFacadeActor.Created](Insert(newBook))) thenReturn Future.successful(DbFacadeActor.Created(newBookId))

    Post("/books", newBook) ~> route ~> check {
      status should equal(Created)
      responseAs[DbFacadeActor.Created] should equal(DbFacadeActor.Created(newBookId))
    }
  }

  it should "allow a user to retrieve all store of a collection via GET to /collections/:id" in {
    val storeId = 2
    val testBooks = List(Book(Some(1), "test-book", storeId))
    when(mockMsgHandler.handleMsg[Seq[Book]](FindBooksBy(storeId))) thenReturn Future.successful(testBooks)

    Get("/stores/" + storeId) ~> route ~> check {
      status should equal(OK)
      responseAs[List[Book]] should equal(testBooks)
    }
  }

}
