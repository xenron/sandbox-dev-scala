package com.packt

import akka.actor._

import scala.io.Source

object FileFinder {
  def props() = Props(new FileFinder)
  case class FileFinderMsg(pat: String, rootDir: String)
}


class FileFinder extends Actor {
  import FileFinder._
  import context._
  import java.io.File

  val grepActor = system.actorOf(Props(new GrepAFile), "GrepAFile")

  def recurseIntoDir(dir: File): Iterable[File] = {
    val itsChildren = new Iterable[File] {
      def iterator = if (dir.isDirectory) dir.listFiles.iterator else Iterator.empty
    }
    Seq(dir) ++: itsChildren.flatMap(recurseIntoDir(_))
  }

  def receive = {
    case FileFinderMsg(pat, rootDir) => {
      val d = new File(rootDir)
      for(f <- recurseIntoDir(d) if f.getName.endsWith(".txt")) {
        import GrepAFile.GrepMsg

        grepActor ! GrepMsg(pat, f)
      }
    }
    case _ => self ! PoisonPill
  }
}

