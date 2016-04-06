package flyingwalrus.bytecask

import org.scalatest._

import flyingwalrus.bytecask.Utils._
import flyingwalrus.bytecask.Bytes._
import flyingwalrus.bytecask.Files._

class BasicPrefixedKeysSuite
    extends FlatSpec
    with Matchers
    with BeforeAndAfterEach {

  "Bytecask" should "do put, get and delete" in {
    val db = new Bytecask(mkTempDir, prefixedKeys = true)
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
  it should "do bulk seq put and get" in {
    val db = new Bytecask(mkTempDir, prefixedKeys = true)
    val length = 2048
    val bytes = randomBytes(length)
    val n = 1000
    throughput("bulk put of " + n + " items", n, n * length) {
      for (i <- 1 to n) (db.put("key_" + i, bytes))
    }
    db.count() should be(n)
    throughput("bulk get of " + n + " items", n, n * length) {
      for (i <- 1 to n) {
        string(db.get("key_" + i).get).length should be(length)
      }
    }
    db.destroy()
  }
  it should "allow bulk concurrent put and get" in {
    val db = new Bytecask(mkTempDir, prefixedKeys = true)
    val length = 2048
    val bytes = randomBytes(length)
    val n = 1000

    val entries = (for (i <- 1 to n) yield ("key_" + i -> bytes)).toMap

    entries.par.foreach {
      entry =>
        db.put(entry._1, entry._2)
    }

    db.count() should be(n)

    entries.par.foreach {
      entry => assert(!db.get(entry._1).isEmpty, "value is not empty")
    }

    db.count() should be(n)

    db.destroy()
  }

}
