object MomAndKids extends App {

  def feedKidsInEvening() = "Feeding kids in the evening"

  def cookFoodForKids() = "Cooking food for my kids"

  def wakeUpEarly() = "Sprightly mum I am - waking up!!!\""

  case class DoctorMom() {
    def herOfficeRoutine() = List("Go to hospital", "See patients", "Perform surgeries") // variable part
  }

  case class DefenceScientistMom() {
    def herOfficeRoutine = List("This mum", "Will keep mum") // Need to maintain secrecy! Can't talk about it!
    // variable part
  }

  def makeADailyRoutineMsg(commonTasks: () => List[String])(officeTasks: () => List[String]) = {
    // the 'template method' algorithm
    val herOfficeTasks = officeTasks() map { x => s"\t${x}" }
    commonTasks() ::: List("My office routine") ::: herOfficeTasks ::: List("No more shop talk")
  }

  def printDailyRoutine(msgList: List[String]) = {
    println("---Daily Routine For a mum---")
    msgList foreach (println(_))
    println("---Thats all a mum can do in a day!---")
  }

  def commonMumTasks = List(wakeUpEarly(), cookFoodForKids(), feedKidsInEvening())

  val mom1 = DoctorMom()
  printDailyRoutine(makeADailyRoutineMsg(commonMumTasks _)(mom1.herOfficeRoutine _))

  val mom2 = DefenceScientistMom()
  printDailyRoutine(makeADailyRoutineMsg(commonMumTasks _)(mom2.herOfficeRoutine _))

}
