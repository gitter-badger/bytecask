package flyingwalrus.bytecask

import org.scalatest._
import Utils._
import Bytes._
import Files._

class IndexSuite extends FunSuite with Matchers with BeforeAndAfterEach {

  test("index rebuild") {
    val dir = mkTempDir
    var db = new Bytecask(dir)
    db.put("foo", "bar")
    db.put("baz", "tar")
    db.put("bav", "arc")
    db.put("bav", "arv")
    db.count() should be(3)
    string(db.get("bav").get) should be("arv")
    db.delete("baz")
    db.close()
    db = new Bytecask(dir)
    println("index: " + db.index.getMap)
    db.count() should be(2)
    assert(db.get("baz").isEmpty)
    string(db.get("bav").get) should be("arv")
    db.destroy()
  }

}
