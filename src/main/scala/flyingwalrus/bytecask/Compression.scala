package flyingwalrus.bytecask

trait Compression extends Processors {

  val bytecask: Bytecask

  override val processor = Compressor

}
