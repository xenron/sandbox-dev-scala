package controllers

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import models.Task


object TaskController extends Controller {

  val taskForm = Form(
    "taskName" -> nonEmptyText
  )

  def index = Action {
    Redirect(routes.TaskController.tasks)
  }

  def tasks = Action {
    Ok(views.html.index(Task.all, taskForm))
  }

  def newTask = Action {
    implicit request =>
      taskForm.bindFromRequest.fold(
        errors => BadRequest(views.html.index(Task.all, errors)),
        taskName => {
          Task.add(taskName)
          Redirect(routes.TaskController.index)
        }
      )
  }

  def deleteTask(id: Int) = Action {
    Task.delete(id)
    Redirect(routes.TaskController.tasks)
  }

}
