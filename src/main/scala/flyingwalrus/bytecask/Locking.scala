package flyingwalrus.bytecask

import java.util.concurrent.locks.ReentrantReadWriteLock

trait Locking {
  val lock = new ReentrantReadWriteLock
  val read = lock.readLock
  val write = lock.writeLock

  def readLock[T](f: => T): T = {
    read.lock()
    try {
      f
    }
    finally {
      read.unlock()
    }
  }

  def writeLock[T](f: => T): T = {
    write.lock()
    try {
      f
    }
    finally {
      write.unlock()
    }
  }
}
