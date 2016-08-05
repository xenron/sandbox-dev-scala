object ChainOfResponsibility extends App {
  case class Task(description: String)

  type TaskHandler = PartialFunction[Task, Unit]

  def canHandle(phrases: List[String], task: Task) =
    phrases exists (task.description.toLowerCase.contains(_))

  def handleIt(name: String, task: Task) = println(s"${name} Handling: " + task.description)

  val dad: TaskHandler = {
    case task: Task if canHandle(List("wood", "hunt"), task) => handleIt("Dad", task)
  }

  val mom: TaskHandler = {
    case task: Task if canHandle(List("sew", "cook"), task) => handleIt("Mom", task)
  }

  val kid: TaskHandler = {
    case task: Task if canHandle(List("dog", "cat"), task) => handleIt("Kid", task)
  }

  val f = dad orElse mom orElse kid

  // f(Task("feed the cat"))

  val taskList = List("Need to sew up a shirt button",
    "Walk the Dog", "cut some firewood", "feed the cat")

  taskList map (Task(_)) map f
}

