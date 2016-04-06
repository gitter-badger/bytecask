package flyingwalrus.bytecask

import org.scalatest._
import Utils._
import Files._

class JmxSuite extends FunSuite with Matchers with BeforeAndAfterEach {

  test("simple JMX install via trait") {
    val db = new Bytecask(mkTempDir) with JmxSupport
    db.jmxInit()
    //TODO use Jmx client to invoke some method
    db.jmxDestroy()
    db.destroy()
  }

}
