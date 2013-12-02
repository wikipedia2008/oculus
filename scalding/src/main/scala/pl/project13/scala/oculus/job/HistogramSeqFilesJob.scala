package pl.project13.scala.oculus.job

import com.twitter.scalding._
import pl.project13.scala.oculus.IPs
import pl.project13.scala.scalding.hbase.MyHBaseSource
import org.apache.commons.io.FilenameUtils

class HistogramSeqFilesJob(args: Args) extends Job(args)
  with PHashing {

  val input = args("input")

  implicit val mode = Read

  val Hashes = new MyHBaseSource(
    tableName = "histograms",
    quorumNames = IPs.HadoopMasterWithPort,
    keyFields = 'histogram,
    familyNames = Array("youtube"),
    valueFields = Array('id, 'frame)
  )

//  override val youtubeId = FilenameUtils.getBaseName(input)
//
//  WritableSequenceFile(input, ('key, 'value))
//    .read
//    .rename('key, 'frame)
//    .map(('frame, 'value) -> ('id, 'mhHash)) { p: SeqFileElement =>
//      youtubeId.asImmutableBytesWriteable -> mhHash(p)
//    }
//    .write(Hashes)

}
