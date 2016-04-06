package flyingwalrus.bytecask

import flyingwalrus.bytecask.Utils._
import scala.Some

/**
 * Hook to install processing like compression
 */

trait ValueProcessor {
  def before(b: Bytes): Bytes

  def after(b: Option[Bytes]): Option[Bytes]
}

@inline
object PassThru extends ValueProcessor {
  @inline def before(b: Bytes) = b

  @inline def after(b: Option[Bytes]) = b
}

@inline
object Compressor extends ValueProcessor {
  @inline def before(b: Bytes) = compress(b)

  @inline def after(b: Option[Bytes]) = Some(uncompress(b.get))
}

