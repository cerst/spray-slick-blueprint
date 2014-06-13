package skeleton.exception

/**
 * Created by gerstbec on 05.06.2014.
 */
case class PersistenceException(errorType: PersistenceError, msg: String) extends RuntimeException(msg)

sealed trait PersistenceError

object PersistenceError {

  case object NotFound extends PersistenceError

  case object Validation extends PersistenceError

}
