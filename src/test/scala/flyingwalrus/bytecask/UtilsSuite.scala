package flyingwalrus.bytecask

import org.scalatest._
import Utils._

class UtilsSuite extends FunSuite with Matchers with BeforeAndAfterEach {

  test("slot") {
    firstSlot(Array(1, 2, 3, 5)) should be(Some(4))
    firstSlot(Array(1, 2, 4, 5)) should be(Some(3))
    firstSlot(Array(1, 2, 4, 5)) should be(Some(3))
    firstSlot(Array(1, 15)) should be(Some(2))
    firstSlot(Array(1, 2, 3, 4)) should be(None)
  }

  test("dirs") {
    val dir = mkTempDir.getAbsolutePath
    assert(dir.mkFile.exists())
    assert(dirSize(dir) == 0)
    assert(ls(dir).size == 0)
    rmdir(dir)
  }

}
