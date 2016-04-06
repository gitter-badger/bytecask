package flyingwalrus.bytecask

import org.scalatest._
import Utils._
import Files._
import Bytes._
import State._

class StateAwareSuite
    extends FlatSpec
    with Matchers
    with BeforeAndAfterEach {

  "ByteCask" should "be stateful for put and delete" in {
    val db = new Bytecask(mkTempDir)
    db.getState should be(Unmodified)
    db.put("foo", "bar")
    db.getState should be(AfterPut)
    db.delete("foo")
    db.getState should be(AfterPutAndDelete)
    db.put("2foo", "bar")
    db.getState should be(AfterPutAndDelete)
    db.resetState()
    db.getState should be(Unmodified)
    db.put("foo", "bar")
    db.resetState()
    db.delete("foo")
    db.getState should be(AfterDelete)
    db.resetState()
    db.getState should be(Unmodified)
    db.destroy()
  }
  it should "activate and passivate" in {
    val db = new Bytecask(mkTempDir)
    Thread.sleep(300)
    db.idleTime should be >= (300L)
    db.count()
    db.idleTime should be < (300L)
    db.put("1", "foo")
    db.put("2", "bar")
    db.passivate()
    a [IllegalAccessException] should be thrownBy {
      db.count()
    }
    db.activate()
    db.get("1").get.asString should be ("foo")
    db.get("2").get.asString should be ("bar")
    db.destroy()
  }

}
