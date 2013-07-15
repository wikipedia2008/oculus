package pl.project13.scala.oculus.actor

import akka.actor.{ActorLogging, Actor}
import pl.project13.scala.oculus.file.DownloadedVideoFile
import pl.project13.scala.oculus.hdfs.HDFSActions
import pl.project13.scala.oculus.ffmpeg.FFMPEG
import java.io.File

class HDFSUploadActor extends Actor with ActorLogging
  with HDFSActions {

  def receive = {
    case RequestUploadToHDFS(local: DownloadedVideoFile) =>
      self ! UploadFileToHDFS(local)
      self ! UploadAsSequenceFileToHDFS(local)


    case UploadAsSequenceFileToHDFS(local: DownloadedVideoFile) =>
      log.info("Will upload as sequence file [%s] to HDFS...".format(local.fullName))

      val images = FFMPEG.ffmpegToImages(local.file, framesPerSecond = 1)
      uploadAsSequenceFile(images, createTargetPath(local) + ".seq")

      cleanupDownloadedFiles(local, images)


    case UploadFileToHDFS(local: DownloadedVideoFile) =>
      log.info("Will upload plain file [%s] to HDFS...".format(local.fullName))
      upload(local.file, createTargetPath(local), delSrc = false)
  }


  def cleanupDownloadedFiles(local: DownloadedVideoFile, images: List[File]) {
    logger.info("Cleaning up after upload of [%s] based sequence file".format(local.file.getName))
    local.file :: images foreach { _.delete() }
  }

  def createTargetPath(local: DownloadedVideoFile): String =
    "/oculus/source/" + local.fullName

}