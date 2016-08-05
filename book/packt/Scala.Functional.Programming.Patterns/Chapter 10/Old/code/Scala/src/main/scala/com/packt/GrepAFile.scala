package com.packt

import java.io.File

import akka.actor.{ Actor, Props, PoisonPill }

import scala.io.Source

object GrepAFile {
  def props() = Props(new GrepAFile)
  case class GrepMsg(pat: String, file: File)
}

class GrepAFile extends Actor {
  import GrepAFile._

  def receive = {
    case GrepMsg(pat, file) => {
      for (line <- Source.fromFile(file).getLines()) {
        if (line.contains(pat)) {
          println(s"${file.getName} : ${line}")
        }
      }
    }

    case _ => {
      println("GrepAFile exitting")
      self ! PoisonPill
    }
  }
}
