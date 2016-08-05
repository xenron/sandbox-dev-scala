def invokeHandler[T](call: => T, handler: HandlerDef)(implicit d: HandlerInvoker[T]): Handler = {
      d.call(call, handler) match {
        case javaAction: play.core.j.JavaAction => new play.core.j.JavaAction with RequestTaggingHandler {
          def invocation = javaAction.invocation
          val annotations = javaAction.annotations
          val parser = javaAction.annotations.parser
          def tagRequest(rh: RequestHeader) = doTagRequest(rh, handler)
        }
        case action: EssentialAction => new EssentialAction with RequestTaggingHandler {
          def apply(rh: RequestHeader) = action(rh)
          def tagRequest(rh: RequestHeader) = doTagRequest(rh, handler)
        }
        case ws @ WebSocket(f) => {
          WebSocket[ws.FRAMES_TYPE](rh => f(doTagRequest(rh, handler)))(ws.frameFormatter)
        }
        case handler => handler
      } 


case class HandlerDef(ref: AnyRef, routerPackage: String, controller: String, method: String, parameterTypes: Seq[Class[_]], verb: String, comments: String, path: String)


sourceGenerators in Compile <+= (state, confDirectory, sourceManaged in Compile, routesImport, generateReverseRouter, generateRefReverseRouter, namespaceReverseRouter) map {     
 (s, cd, sm, ri, grr, grrr, nrr) => RouteFiles(s, Seq(cd), sm, ri, grr, grrr, nrr)    
},


case class Call(method: String, url: String) extends play.mvc.Call {

    //Transform this call to an absolute URL.
    def absoluteURL(secure: Boolean = false)(implicit request: RequestHeader) = {
      "http" + (if (secure) "s" else "") + "://" + request.host + this.url
    }

    // Transform this call to an WebSocket URL.
    def webSocketURL(secure: Boolean = false)(implicit request: RequestHeader) = {
      "ws" + (if (secure) "s" else "") + "://" + request.host + this.url
    }

    override def toString = url

  }


def at(path: String, file: String, aggressiveCaching: Boolean = false): Action[AnyContent] = Action.async {
    implicit request =>

      import Implicits.trampoline
      val pendingResult: Future[Result] = for {
        Some(name) <- Future.successful(resourceNameAt(path, file))
        (assetInfo, gzipRequested) <- assetInfoForRequest(request, name)
      } yield {
        val stream = assetInfo.url(gzipRequested).openStream()
        Try(stream.available -> Enumerator.fromStream(stream)(Implicits.defaultExecutionContext)).map {
          case (length, resourceData) =>
            maybeNotModified(request, assetInfo, aggressiveCaching).getOrElse {
              cacheableResult(
                assetInfo,
                aggressiveCaching,
                result(file, length, assetInfo.mimeType, resourceData, gzipRequested, assetInfo.gzipUrl.isDefined)
              )
            }
        }.getOrElse(NotFound)
      }

      pendingResult.recover {
        case e: InvalidUriEncodingException =>
          Logger.debug(s"Invalid URI encoding for $file at $path", e)
          BadRequest
        case e: Throwable =>
          Logger.debug(s"Unforseen error for $file at $path", e)
          NotFound
      }
  }


def singleComponentPathPart: Parser[DynamicPart] = (":" ~> identifier) ^^ {
      case name => DynamicPart(name, """[^/]+""", encode = true)
    }

    def multipleComponentsPathPart: Parser[DynamicPart] = ("*" ~> identifier) ^^ {
      case name => DynamicPart(name, """.+""", encode = false)
    }

    def regexComponentPathPart: Parser[DynamicPart] = "$" ~> identifier ~ ("<" ~> (not(">") ~> """[^\s]""".r +) <~ ">" ^^ { case c => c.mkString }) ^^ {
      case name ~ regex => DynamicPart(name, regex, encode = false)
    }

    def staticPathPart: Parser[StaticPart] = (not(":") ~> not("*") ~> not("$") ~> """[^\s]""".r +) ^^ {
      case chars => StaticPart(chars.mkString)
    }


trait PathPart

case class DynamicPart(name: String, constraint: String, encode: Boolean) extends PathPart with Positional {
  override def toString = """DynamicPart("""" + name + "\", \"\"\"" + constraint + "\"\"\"," + encode + ")" //"
}

case class StaticPart(value: String) extends PathPart {
  override def toString = """StaticPart("""" + value + """")"""
}

case class PathPattern(parts: Seq[PathPart]) {
  def has(key: String): Boolean = parts.exists {
    case DynamicPart(name, _, _) if name == key => true
    case _ => false
  }

  override def toString = parts.map {
    case DynamicPart(name, constraint, encode) => "$" + name + "<" + constraint + ">"
    case StaticPart(path) => path
  }.mkString

}
