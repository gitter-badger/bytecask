package flyingwalrus.bytecask

import flyingwalrus.bytecask.Utils._
import flyingwalrus.bytecask.Files._
import java.io.{FileInputStream, FileOutputStream}
import org.scalatest._

class BlobStoreSuite extends FunSuite with Matchers with BeforeAndAfterEach {

  test("store and retrieve") {
    val db = new Bytecask(mkTempDir) with BlobStore {
      val blockSize = 1024 * 1024
    }

    val file = "/tmp/blob"
    withResource(new FileOutputStream(file)) {
      os =>
        val buf = new Array[Byte](1024)
        for (i <- 0 to 11000) {
          scala.util.Random.nextBytes(buf)
          os.write(buf)
        }
    }

    val name = "blobby"
    db.storeBlob(name, new FileInputStream(file))

    db.count() should be(12) //11 chunks + 1 descriptor

    val meta = db.getBlobMetadata(name)
    meta.name should be(name)
    meta.length should be(11001 * 1024)
    meta.blocks should be(11)

    val file2 = "/tmp/blob2"
    withResource(new FileOutputStream(file2)) {
      os => db.retrieveBlob(name, os)
    }

    file.mkFile.length() should be(file2.mkFile.length())

    db.destroy()

    file.mkFile.delete()
    file2.mkFile.delete()
  }

}
