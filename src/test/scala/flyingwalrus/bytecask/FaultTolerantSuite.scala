package flyingwalrus.bytecask

import org.scalatest._
import Utils._
import Bytes._
import Files._
import java.io.RandomAccessFile

class FaultTolerantSuite extends FunSuite with Matchers with BeforeAndAfterEach with TestSupport {

  test("corrupted file during indexing - read until error") {
    val dir = mkTempDir
    var db = new Bytecask(dir)
    db.put("1", randomBytes(1024))
    db.put("2", randomBytes(1024))
    db.close()
    val raf = new RandomAccessFile(ls(dir).head, "rw")
    raf.seek(1024 + 32)
    raf.write(randomBytes(128))
    raf.close()
    db = new Bytecask(dir)
    db.index.size should be(1)
    assert(!db.get("1").isEmpty)
    db.index.errorCount() should be(1)
    db.destroy()
  }


}
