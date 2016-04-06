package flyingwalrus.bytecask

import org.scalatest._
import flyingwalrus.bytecask.Utils._
import flyingwalrus.bytecask.Files._
import flyingwalrus.bytecask.Bytes._

class EvictionSuite extends FunSuite with Matchers with BeforeAndAfterEach {

  test("eviction when maxCount is 3") {
    val db = new Bytecask(mkTempDir) with Eviction {
      val maxCount = 3
    }
    db.put("foo", "bar")
    db.put("baz", "boo")
    db.put("baz1", "boo1")
    db.count() should be(3)
    db.performEviction()
    db.count() should be(3)
    db.put("1foo", "bar")
    db.put("2foo", "bar")
    db.count() should be(5)
    db.performEviction()
    db.count() should be(3)
    db.destroy()
  }

}
