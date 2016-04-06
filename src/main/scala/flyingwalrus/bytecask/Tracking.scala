package flyingwalrus.bytecask

import java.util.concurrent.atomic.AtomicInteger

trait Tracking {
  val errors = new AtomicInteger

  def errorCount() = errors.get

  def incErrors = errors.incrementAndGet()
}
