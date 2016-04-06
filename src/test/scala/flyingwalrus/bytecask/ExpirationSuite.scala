package flyingwalrus.bytecask

import org.scalatest._
import Utils._
import Files._
import Bytes._

class ExpirationSuite extends FunSuite with Matchers with BeforeAndAfterEach {

  test("expiration when ttl is 1 sec") {
    val db = new Bytecask(mkTempDir) with Expiration {
      val ttl = 1
    }
    db.put("foo", "bar")
    db.put("baz", "boo")
    db.put("baz1", "boo1")
    db.count() should be(3)
    db.performExpiration()
    db.count() should be(3)
    Thread.sleep(1001)
    db.performExpiration()
    db.count() should be(0)
    db.destroy()
  }

}
