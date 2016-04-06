package flyingwalrus.bytecask

import org.scalatest._
import Utils._
import Bytes._
import Files._

class MergeSuite extends FunSuite with Matchers with BeforeAndAfterEach {

  test("merge") {
    var db = new Bytecask(mkTempDir, maxFileSize = 1024)
    db.put("4", randomBytes(128))
    db.put("5", randomBytes(128))
    db.put("1", randomBytes(4096))
    db.put("2", randomBytes(4096))
    db.put("3", randomBytes(4096))
    db.delete("2")
    db.delete("3")
    db.delete("4")
    db.delete("5")
    val s0 = dirSize(db.dir)
    db.filesCount should be(4)
    assert(db.reclaimSize() > 0)
    db.merge()
    assert(db.reclaimSize() == 0)
    val s1 = dirSize(db.dir)
    db.filesCount should be(2 + 1)
    println("size before: %s, after: %s ".format(s0, s1))
    assert(s1 < s0)
    assert(db.get("2").isEmpty)
    assert(db.get("3").isEmpty)
    assert(db.get("4").isEmpty)
    assert(db.get("5").isEmpty)
    assert(!db.get("1").isEmpty)
    db.merger.mergesCount.get should be(1)
    assert(ls(db.dir).toList.map(_.getName).contains("1h"))
    db.close()
    db = new Bytecask(db.dir)
    println(db.index.getMap)
    assert(db.get("2").isEmpty)
    assert(db.get("3").isEmpty)
    assert(db.get("4").isEmpty)
    assert(db.get("5").isEmpty)
    assert(!db.get("1").isEmpty)
    db.destroy()
  }

  test("merge2") {
    var db = new Bytecask(mkTempDir, maxFileSize = 1024)
    db.put("11", randomBytes(128))
    db.put("12", randomBytes(1128))
    db.put("1", randomBytes(1024))
    db.put("2", randomBytes(6))
    db.put("3", randomBytes(4096))
    db.delete("11")
    db.delete("12")
    db.delete("2")
    db.delete("3")
    db.merge()
    db.filesCount should be(2 + 1)
    println(collToString(ls(db.dir).toList))
    println(db.index.getMap)
    assert(db.get("11").isEmpty)
    assert(db.get("12").isEmpty)
    assert(db.get("2").isEmpty)
    assert(db.get("3").isEmpty)
    assert(!db.get("1").isEmpty)
    assert(ls(db.dir).toList.map(_.getName).contains("1h"))
    db.close()
    db = new Bytecask(db.dir)
    assert(db.get("11").isEmpty)
    assert(db.get("12").isEmpty)
    assert(db.get("2").isEmpty)
    assert(db.get("3").isEmpty)
    assert(!db.get("1").isEmpty)
    db.destroy()
  }

  test("skip merge") {
    val db = new Bytecask(mkTempDir, maxFileSize = 1024)
    db.put("foo", randomBytes(128))
    val s0 = dirSize(db.dir)
    db.filesCount should be(1)
    db.merge()
    val s1 = dirSize(db.dir)
    db.filesCount should be(1)
    println("size before: %s, after: %s ".format(s0, s1))
    assert(s1 == s0)
    assert(!db.get("foo").isEmpty)
    db.merger.mergesCount.get should be(0)
    db.destroy()
  }

}
