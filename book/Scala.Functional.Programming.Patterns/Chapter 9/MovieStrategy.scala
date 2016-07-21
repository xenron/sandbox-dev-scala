object MovieStrategy extends App {

  type MoviePref = () => String

  abstract class FamilyMember {
    def name: String
    def moviePref: MoviePref
  }
  case class Mom(name: String, moviePref: MoviePref) extends
    FamilyMember
  case class Dad(name: String, moviePref: MoviePref) extends
    FamilyMember
  case class Kid(name: String, moviePref: MoviePref) extends
    FamilyMember

  def letUsGoForAMovie[T <: FamilyMember](familyMember: T) = {
    val mp = s"${familyMember.name} :  ${familyMember
      .moviePref() } "
    println(mp)
  }

  val mom = Mom("Mom", () => "some romantic flick")
  val dad = Dad("Dad", () => "Action! Guns! Chase")
  val kid = Kid("Kid", () => "Animation for me")

  letUsGoForAMovie(mom) // 3
  letUsGoForAMovie(dad)
  letUsGoForAMovie(kid)
}

