trait EssentialAction extends (RequestHeader => Iteratee[Array[Byte], Result]) with Handler {

 def apply() = this

}

object EssentialAction {

  def apply(f: RequestHeader => Iteratee[Array[Byte], Result]): EssentialAction = new EssentialAction {
    def apply(rh: RequestHeader) = f(rh)
  }
}

trait Action[A] extends EssentialAction {

  //Type of the request body.
  type BODY_CONTENT = A

  //Body parser associated with this action.
  def parser: BodyParser[A]

  //Invokes this action
  def apply(request: Request[A]): Future[Result]

  def apply(rh: RequestHeader): Iteratee[Array[Byte], Result] = parser(rh).mapM {
    case Left(r) =>
      Future.successful(r)
    case Right(a) =>
      val request = Request(rh, a)
      Play.maybeApplication.map { app =>
        play.utils.Threads.withContextClassLoader(app.classloader) {
          apply(request)
        }
      }.getOrElse {
        apply(request)
      }
  }(executionContext)

  //The execution context to run this action in
  def executionContext: ExecutionContext = play.api.libs.concurrent.Execution.defaultContext

  //Returns itself, for better support in the routes file.
  override def apply(): Action[A] = this

  override def toString = {
    "Action(parser=" + parser + ")"
  }

}

final def apply(block: R[AnyContent] => Result): Action[AnyContent] = apply(BodyParsers.parse.anyContent)(block)

def anyContent: BodyParser[AnyContent] = BodyParser("anyContent") { request =>
      import play.api.libs.iteratee.Execution.Implicits.trampoline
      request.contentType.map(_.toLowerCase(Locale.ENGLISH)) match {
        case _ if request.method == HttpVerbs.GET || request.method == HttpVerbs.HEAD => {
          Play.logger.trace("Parsing AnyContent as empty")
          empty(request).map(_.right.map(_ => AnyContentAsEmpty))
        }
        case Some("text/plain") => {
          Play.logger.trace("Parsing AnyContent as text")
          text(request).map(_.right.map(s => AnyContentAsText(s)))
        }
        case Some("text/xml") | Some("application/xml") | Some(ApplicationXmlMatcher()) => {
          Play.logger.trace("Parsing AnyContent as xml")
          xml(request).map(_.right.map(x => AnyContentAsXml(x)))
        }
        case Some("text/json") | Some("application/json") => {
          Play.logger.trace("Parsing AnyContent as json")
          json(request).map(_.right.map(j => AnyContentAsJson(j)))
        }
        case Some("application/x-www-form-urlencoded") => {
          Play.logger.trace("Parsing AnyContent as urlFormEncoded")
          urlFormEncoded(request).map(_.right.map(d => AnyContentAsFormUrlEncoded(d)))
        }
        case Some("multipart/form-data") => {
          Play.logger.trace("Parsing AnyContent as multipartFormData")
          multipartFormData(request).map(_.right.map(m => AnyContentAsMultipartFormData(m)))
        }
        case _ => {
          Play.logger.trace("Parsing AnyContent as raw")
          raw(request).map(_.right.map(r => AnyContentAsRaw(r)))
        }
      }
    }

case class Result(header: ResponseHeader, body: Enumerator[Array[Byte]],
                  connection: HttpConnection.Connection = HttpConnection.KeepAlive) {

  // Adds headers to this result.
  def withHeaders(headers: (String, String)*): Result = {
    copy(header = header.copy(headers = header.headers ++ headers))
  }

  // Adds cookies to this result.
  def withCookies(cookies: Cookie*): Result = {
    withHeaders(SET_COOKIE -> Cookies.merge(header.headers.get(SET_COOKIE).getOrElse(""), cookies))
  }

  // Discards cookies along this result.
  def discardingCookies(cookies: DiscardingCookie*): Result = {
    withHeaders(SET_COOKIE -> Cookies.merge(header.headers.get(SET_COOKIE).getOrElse(""), cookies.map(_.toCookie)))
  }

  // Sets a new session for this result.
  def withSession(session: Session): Result = {
    if (session.isEmpty) discardingCookies(Session.discard) else withCookies(Session.encodeAsCookie(session))
  }

  //Sets a new session for this result, discarding the existing session.
  def withSession(session: (String, String)*): Result = withSession(Session(session.toMap))

  //Discards the existing session for this result.
  def withNewSession: Result = withSession(Session())

  //Adds values to the flash scope for this result.
  def flashing(flash: Flash): Result = {
    if (shouldWarnIfNotRedirect) {
      logRedirectWarning("flashing")
    }
    withCookies(Flash.encodeAsCookie(flash))
  }

  //Adds values to the flash scope for this result.
  def flashing(values: (String, String)*): Result = flashing(Flash(values.toMap))

  //Changes the result content type.
  def as(contentType: String): Result = withHeaders(CONTENT_TYPE -> contentType)

  // Reads the request’s session if this result does not modify the session.
  def session(implicit request: RequestHeader): Session =
    Cookies(header.headers.get(SET_COOKIE)).get(Session.COOKIE_NAME) match {
      case Some(cookie) => Session.decodeFromCookie(Some(cookie))
      case None => request.session
    }

  // Adds key value pairs to session
  def addingToSession(values: (String, String)*)(implicit request: RequestHeader): Result =
    withSession(new Session(session.data ++ values.toMap))

  // removes keys from session
  def removingFromSession(keys: String*)(implicit request: RequestHeader): Result =
    withSession(new Session(session.data -- keys))

  //Sets the user's language permanently for future requests by storing it in a cookie.
  def withLang(lang: Lang)(implicit app: Application): Result =
    withCookies(Cookie(Play.langCookieName, lang.code))

  //Clears the user's language by discarding the language cookie set by withLang
  def clearingLang(implicit app: Application): Result =
    discardingCookies(DiscardingCookie(Play.langCookieName))

  override def toString = {
    "Result(" + header + ")"
  }

  //Returns true if the status code is not 3xx and the application is in Dev mode.
  private def shouldWarnIfNotRedirect: Boolean = {
    play.api.Play.maybeApplication.exists(app =>
      (app.mode == play.api.Mode.Dev) && (header.status < 300 || header.status > 399))
  }

  //Logs a redirect warning.
  private def logRedirectWarning(methodName: String) {
    val status = header.status
    play.api.Logger("play").warn(s"You are using status code '$status' with $methodName, which should only be used with a redirect status!")
  }

}

val TODO = Action {
    NotImplemented[play.api.templates.Html](views.html.defaultpages.todo())
  }

// Provides helpers for creating `Action` values. 
trait ActionBuilder[R[_]] { 
  self => 

  //Constructs an `Action`. 
  final def apply[A](bodyParser: BodyParser[A])(block: R[A] => Result): Action[A] = async(bodyParser) { req: R[A] => 
    Future.successful(block(req)) 
  } 

  //Constructs an `Action` with default content. 
  final def apply(block: R[AnyContent] => Result): Action[AnyContent] = apply(BodyParsers.parse.anyContent)(block) 
 
  //Constructs an `Action` with default content, and no request parameter. 
  final def apply(block: => Result): Action[AnyContent] = apply(_ => block) 

  //Constructs an `Action` that returns a future of a result, with default content, and no request parameter. 
  final def async(block: => Future[Result]): Action[AnyContent] = async(_ => block) 

  //Constructs an `Action` that returns a future of a result, with default content. 
   final def async(block: R[AnyContent] => Future[Result]): Action[AnyContent] = async(BodyParsers.parse.anyContent)(block) 

  //Constructs an `Action` that returns a future of a result, with default content. 
  final def async[A](bodyParser: BodyParser[A])(block: R[A] => Future[Result]): Action[A] = composeAction(new Action[A] { 
    def parser = composeParser(bodyParser) 
    def apply(request: Request[A]) = try { 
      invokeBlock(request, block) 
    } catch { 
      // NotImplementedError is not caught by NonFatal, wrap it 
      case e: NotImplementedError => throw new RuntimeException(e) 
      // LinkageError is similarly harmless in Play Framework, since automatic reloading could easily trigger it 
      case e: LinkageError => throw new RuntimeException(e) 
    } 
    override def executionContext = ActionBuilder.this.executionContext 
  }) 

  /** Invoke the block.  This is the main method that an ActionBuilder has to implement, at this stage it can wrap it in 
   * any other actions, modify the request object or potentially use a different class to represent the request. 
   */ 
  protected def invokeBlock[A](request: Request[A], block: R[A] => Future[Result]): Future[Result] 

  //Compose the parser.  This allows the action builder to potentially intercept requests before they are parsed. 
  protected def composeParser[A](bodyParser: BodyParser[A]): BodyParser[A] = bodyParser 

  //Compose the action with other actions.  This allows mixing in of various actions together. 
  protected def composeAction[A](action: Action[A]): Action[A] = action 

  //Get the execution context to run the request in.  Override this if you want a custom execution context 
  protected def executionContext: ExecutionContext = play.api.libs.concurrent.Execution.defaultContext 
}

trait Rendering {

  object render {

    //Tries to render the most acceptable result according to the request’s Accept header value.
    def apply(f: PartialFunction[MediaRange, Result])(implicit request: RequestHeader): Result = {
      def _render(ms: Seq[MediaRange]): Result = ms match {
        case Nil => NotAcceptable
        case Seq(m, ms @ _*) =>
          f.applyOrElse(m, (m: MediaRange) => _render(ms))
      }

      // “If no Accept header field is present, then it is assumed that the client accepts all media types.”
      val result =
        if (request.acceptedTypes.isEmpty) _render(Seq(new MediaRange("*", "*", Nil, None, Nil)))
        else _render(request.acceptedTypes)
      result.withHeaders(VARY -> ACCEPT)
    }

    /**Tries to render the most acceptable result according to the request’s Accept header value.
     * This function can be used if you want to do asynchronous processing in your render function.
     */
    def async(f: PartialFunction[MediaRange, Future[Result]])(implicit request: RequestHeader): Future[Result] = {
      def _render(ms: Seq[MediaRange]): Future[Result] = ms match {
        case Nil => Future.successful(NotAcceptable)
        case Seq(m, ms @ _*) =>
          f.applyOrElse(m, (m: MediaRange) => _render(ms))
      }

      // “If no Accept header field is present, then it is assumed that the client accepts all media types.”
      val result =
        if (request.acceptedTypes.isEmpty) _render(Seq(new MediaRange("*", "*", Nil, None, Nil)))
        else _render(request.acceptedTypes)
      result.map(_.withHeaders(VARY -> ACCEPT))
    }
  }
}

trait RequestExtractors extends AcceptExtractors {

  //Convenient extractor allowing to apply two extractors.
  object & {
    def unapply(request: RequestHeader): Option[(RequestHeader, RequestHeader)] = Some((request, request))
  }

}

//Define a set of extractors allowing to pattern match on the Accept HTTP header of a request
trait AcceptExtractors {

  //Common extractors to check if a request accepts JSON, Html, etc.
  object Accepts {
    import play.api.http.MimeTypes
    val Json = Accepting(MimeTypes.JSON)
    val Html = Accepting(MimeTypes.HTML)
    val Xml = Accepting(MimeTypes.XML)
    val JavaScript = Accepting(MimeTypes.JAVASCRIPT)
  }

}

//Convenient class to generate extractors checking if a given mime type matches the Accept header of a request.
case class Accepting(val mimeType: String) {
  def unapply(request: RequestHeader): Boolean = request.accepts(mimeType)
  def unapply(mediaRange: play.api.http.MediaRange): Boolean = mediaRange.accepts(mimeType)
}

object Filter {
  def apply(filter: (RequestHeader => Future[Result], RequestHeader) => Future[Result]): Filter = new Filter {
    def apply(f: RequestHeader => Future[Result])(rh: RequestHeader): Future[Result] = filter(f, rh)
  }
}

object Filters {
  def apply(h: EssentialAction, filters: EssentialFilter*) = h match {
    case a: EssentialAction => FilterChain(a, filters.toList)
    case h => h
  }
}

object FilterChain {
  def apply[A](action: EssentialAction, filters: List[EssentialFilter]): EssentialAction = new EssentialAction {
    def apply(rh: RequestHeader): Iteratee[Array[Byte], Result] = {
      val chain = filters.reverse.foldLeft(action) { (a, i) => i(a) }
      chain(rh)
    }
  }
} 

object Action extends ActionBuilder[Request] {
  def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = block(request)
}

class WrappedRequest[A](request: Request[A]) extends Request[A] {
    def id = request.id
    def tags = request.tags
    def body = request.body
    def headers = request.headers
    def queryString = request.queryString
    def path = request.path
    def uri = request.uri
    def method = request.method
    def version = request.version
    def remoteAddress = request.remoteAddress
    def secure = request.secure
  }
