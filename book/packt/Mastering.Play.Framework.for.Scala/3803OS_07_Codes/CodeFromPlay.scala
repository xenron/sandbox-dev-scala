class NettyServer(appProvider: ApplicationProvider,
 port: Option[Int],
 sslPort: Option[Int] = None,
 address: String = "0.0.0.0",
 val mode: Mode.Mode = Mode.Prod) extends Server with ServerWithStop { … }

trait ApplicationProvider {
  def path: File
  def get: Try[Application]
  def handleWebCommand(requestHeader: play.api.mvc.RequestHeader): Option[Result] = None
}

def start(app: Application) {

    // First stop previous app if exists
    stop()

    _currentApp = app

    // Ensure routes are eagerly loaded, so that the reverse routers are correctly 
    // initialised before plugins are started.
    app.routes
    Threads.withContextClassLoader(classloader(app)) {
      app.plugins.foreach(_.onStart())
    }

    app.mode match {
      case Mode.Test =>
      case mode => logger.info("Application started (" + mode + ")")
    }

  }

override def stop() {

    try {
      Play.stop()
    } catch {
      case NonFatal(e) => Play.logger.error("Error while stopping the application", e)
    }

    try {
      super.stop()
    } catch {
      case NonFatal(e) => Play.logger.error("Error while stopping logger", e)
    }

    mode match {
      case Mode.Test =>
      case _ => Play.logger.info("Stopping server...")
    }

    // First, close all opened sockets
    allChannels.close().awaitUninterruptibly()

    // Release the HTTP server
    HTTP.foreach(_._1.releaseExternalResources())

    // Release the HTTPS server if needed
    HTTPS.foreach(_._1.releaseExternalResources())

    mode match {
      case Mode.Dev =>
        Invoker.lazySystem.close()
        Execution.lazyContext.close()
      case _ => ()
    }
  }

def stop() {
    Option(_currentApp).map { app =>
      Threads.withContextClassLoader(classloader(app)) {
        app.plugins.reverse.foreach { p =>
          try { 
		p.onStop() 
	    } catch { case NonFatal(e) => logger.warn("Error stopping plugin", e) }
        }
      }
    }
    _currentApp = null
  }

def global: GlobalSettings

private[play] def handleError(request: RequestHeader,
                              e: Throwable): Future[Result] = try {
    e match {
      case e: UsefulException => throw e
      case e: ExecutionException => handleError(request, e.getCause)
      case e: Throwable => {

        val source = sources.flatMap(_.sourceFor(e))

        throw new PlayException.ExceptionSource(
          "Execution exception",
          "[%s: %s]".format(e.getClass.getSimpleName, e.getMessage),
          e) {
          def line = source.flatMap(_._2).map(_.asInstanceOf[java.lang.Integer]).orNull
          def position = null
          def input = source.map(_._1).map(PlayIO.readFileAsString).orNull
          def sourceName = source.map(_._1.getAbsolutePath).orNull
        }
      }
    }
  } catch {
    case NonFatal(e) => try {
      Logger.error(
        """
        |
        |! %sInternal server error, for (%s) [%s] ->
        |""".stripMargin.format(e match {
          case p: PlayException => "@" + p.id + " - "
          case _ => ""
        }, request.method, request.uri),
        e
      )
      global.onError(request, e)
    } catch {
      case NonFatal(e) => DefaultGlobal.onError(request, e)
    }
  }

class DefaultApplication(
  override val path: File,
  override val classloader: ClassLoader,
  override val sources: Option[SourceMapper],
  override val mode: Mode.Mode) extends Application with WithDefaultConfiguration with WithDefaultGlobal with WithDefaultPlugins


class StaticApplication(applicationPath: File) extends ApplicationProvider {

  val application = new DefaultApplication(applicationPath, this.getClass.getClassLoader, None, Mode.Prod)

  Play.start(application)

  def get = Success(application)
  def path = applicationPath
}


class ReloadableApplication(buildLink: BuildLink, buildDocHandler: BuildDocHandler) extends ApplicationProvider {
...
// First, stop the old application if it exists
              Play.stop()

              val newApplication = new DefaultApplication(reloadable.path, projectClassloader, Some(new SourceMapper {
                def sourceOf(className: String, line: Option[Int]) = {
                  Option(buildLink.findSource(className, line.map(_.asInstanceOf[java.lang.Integer]).orNull)).flatMap {
                    case Array(file: java.io.File, null) => Some((file, None))
                    case Array(file: java.io.File, line: java.lang.Integer) => Some((file, Some(line)))
                    case _ => None
                  }
                }
              }), Mode.Dev) with DevSettings {
                import scala.collection.JavaConverters._
                lazy val devSettings: Map[String, String] = buildLink.settings.asScala.toMap
              }

              Play.start(newApplication)
...
}


case Step.Error(msg, _) => {
    e.getChannel.setReadable(true)
    val error = new RuntimeException("Body parser iteratee in error: " + msg)
    val result = app.map(_.handleError(requestHeader, error)).getOrElse(DefaultGlobal.onError(requestHeader, error))


lazy val routes: Option[Router.Routes] = loadRoutes

protected def loadRoutes: Option[Router.Routes] = try {
    Some(classloader.loadClass(configuration.getString("application.router").map(_ + "$").getOrElse("Routes$")).getDeclaredField("MODULE$").get(null).asInstanceOf[Router.Routes]).map { router =>
      router.setPrefix(configuration.getString("application.context").map { prefix =>
        if (!prefix.startsWith("/")) {
          throw configuration.reportError("application.context", "Invalid application context")
        }
        prefix
      }.getOrElse("/"))
      router
    }
  } catch {
    case e: ClassNotFoundException => configuration.getString("application.router").map { routerName =>
      throw configuration.reportError("application.router", "Router not found: " + routerName)
    }
  }

object Global extends WithFilters(new CSRFFilter()) with GlobalSettings

class WithFilters(filters: EssentialFilter*) extends GlobalSettings {
  override def doFilter(a: EssentialAction): EssentialAction = {
    Filters(super.doFilter(a), filters: _*)
  }
} 
