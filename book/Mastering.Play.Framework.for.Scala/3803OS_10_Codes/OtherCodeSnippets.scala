import play.api.Logger
object Task{
  def delete(id:Long) = {
    logger.debug(s"deleting task with id $id")
    ...
  }
}

import play.api.Logger
object Task{
  private lazy val taskLogger = Logger(getClass)
  def delete(id:Long) = {
    taskLogger.debug(s"deleting task with id $id")
    ...
  }
}

import play.api.Logger
object Task{
  private lazy val taskLogger = Logger("application.model")
  def delete(id:Long) = {
    taskLogger.debug(s"deleting task with id $id")
    ...
  }
}
