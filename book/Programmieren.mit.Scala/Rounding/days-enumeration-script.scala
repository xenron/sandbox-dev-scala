// code-examples/Rounding/days-enumeration-script.scala

object WeekDay extends Enumeration {
  type WeekDay = Value
  val Mo, Di, Mi, Do, Fr, Sa, So = Value
}
import WeekDay._

def isWorkingDay(d: WeekDay) = ! (d == Sa || d == So)

WeekDay.values filter isWorkingDay foreach println
