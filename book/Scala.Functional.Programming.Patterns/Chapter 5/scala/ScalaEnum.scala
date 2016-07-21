object ScalaEnum extends App {
	sealed trait WeekDay
	case object Sun extends WeekDay
	case object Mon extends WeekDay
	case object Tue extends WeekDay
	case object Wed extends WeekDay
	case object Thu extends WeekDay
	case object Fri extends WeekDay
	case object Sat extends WeekDay

	def m(p: WeekDay) = println(p)

	m(Sat)
}
