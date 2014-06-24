package skeleton.util

import skeleton.exception.{PersistenceError, PersistenceException}

/**
 * Created by gerstbec on 24.06.2014.
 */
trait DbAccessUtil {

  @throws[PersistenceException]
  def wrapException[T](exp: => T): T = try {
    exp
  } catch {
    case t: Throwable => throw new PersistenceException(PersistenceError.Unexpected, t.getMessage, t.getCause)
  }

}
