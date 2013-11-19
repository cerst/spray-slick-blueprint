package skeleton.store.boundary

import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}
import spray.testkit.ScalatestRouteTest
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import scala.reflect.ClassTag
import scala.concurrent.Future
import akka.actor.ActorRefFactory
import skeleton.testutil.MockMsgHandler
import skeleton.store.entity.{BooksReq, IdRsp, InsertReq, Book}
import skeleton.util.ErrorMsg
import spray.http.StatusCodes.{Created, OK}

class StoreRouteSpec extends FlatSpec with Matchers with BeforeAndAfter with MockitoSugar with ScalatestRouteTest {

  var mockMsgHandler: MockMsgHandler = _

  val testStoreRoute = new StoreRoute {
    implicit def actorRefFactory: ActorRefFactory = system

    def handleRestMsg[T](msg: Any)(implicit tag: ClassTag[T]): Future[T] = mockMsgHandler.handleMsg[T](msg)
  }

  def route = testStoreRoute.route

  before {
    mockMsgHandler = mock[MockMsgHandler]
  }

  import spray.httpx.SprayJsonSupport._
  import JsonProtocol._

  "The store route" should "allow a user to create a new book via POST to /books" in {
    val newBook = Book(None, "test-book", 1)
    val newBookId = 1L
    when(mockMsgHandler.handleMsg[Either[Long, ErrorMsg]](InsertReq(newBook))) thenReturn Future.successful(Left(newBookId))

    Post("/books", newBook) ~> route ~> check {
      status should equal(Created)
      responseAs[IdRsp] should equal(IdRsp(newBookId))
    }
  }

  it should "allow a user to retrieve all store of a collection via GET to /collections/:id" in {
    val collectionId = 2
    val testBooks = List(Book(Some(1), "test-book", collectionId))
    when(mockMsgHandler.handleMsg[List[Book]](BooksReq(collectionId))) thenReturn Future.successful(testBooks)

    Get("/collections/" + collectionId) ~> route ~> check {
      status should equal(OK)
      responseAs[List[Book]] should equal(testBooks)
    }
  }

}
