package flyingwalrus.bytecask

import java.util.Arrays

/*
Internal byte array representation with equals to compare array values
 */

final class Bytes(val bytes: Array[Byte]) {

  override def equals(other: Any) = {
    if (!other.isInstanceOf[Bytes]) false
    else Arrays.equals(bytes, other.asInstanceOf[Bytes].bytes)
  }

  def size = bytes.size

  def asString = new String(bytes)

  def isEmpty = bytes.isEmpty

  override def hashCode = Arrays.hashCode(bytes)
}

object Bytes {
  lazy val EMPTY = Bytes(Array[Byte]())

  def apply(bytes: Array[Byte]) = new Bytes(bytes)

  def apply(s: String) = new Bytes(s.getBytes)

  implicit def arrToBytes(bytes: Array[Byte]) = Bytes(bytes)

  implicit def strToBytes(s: String) = Bytes(s.getBytes)

  implicit def byteToBytes(i: Byte) = Bytes(Array(i))

  implicit def intToBytes(i: Int) = Array(i.toByte)

  implicit def toArray(bytes: Bytes) = bytes.bytes

  implicit def strToArray(s: String) = s.getBytes

  implicit def bytesToString(b: Array[Byte]) = b.asString
}


