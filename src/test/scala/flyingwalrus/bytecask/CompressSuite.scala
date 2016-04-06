package flyingwalrus.bytecask

import Utils._
import Files._
import Bytes._
import org.scalatest._

class CompressSuite extends FunSuite with Matchers with BeforeAndAfterEach {

  test("compressing 64K") {
    val c = randomString(1024 * 64).getBytes
    val d = uncompress(compress(c))
    Bytes(c) should be(Bytes(d))
  }

  test("compressing 1 char long") {
    val c = "1".getBytes
    val d = uncompress(compress(c))
    c should be(d)
  }

  test("basic ops with compression") {
    val db = new Bytecask(mkTempDir) with Compression
    db.put("foo", "bar")
    db.put("baz", "boo")
    string(db.get("foo").get) should be("bar")
    string(db.get("baz").get) should be("boo")
    db.keys().map(string) should be(Set("foo", "baz"))
    db.values().size should be(2)
    db.delete("foo")
    db.get("foo") should be(None)
    db.destroy()
  }

  test("basic ops with compression and prefixed keys") {
    val db = new Bytecask(mkTempDir, prefixedKeys = true) with Compression
    db.put("foo", "bar")
    db.put("baz", "boo")
    string(db.get("foo").get) should be("bar")
    string(db.get("baz").get) should be("boo")
    println(db.keys.map(string))
    println(db.index.getMap)
    db.keys().map(string) should be(Set("foo", "baz"))
    db.values().size should be(2)
    db.delete("foo")
    db.get("foo") should be(None)
    db.destroy()
  }

}
