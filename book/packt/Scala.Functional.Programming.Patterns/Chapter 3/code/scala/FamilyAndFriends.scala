object FamilyAndFriends extends App {
  abstract class Friend(name: String) {
    def visit(husband: Husband): Unit
    def visit(wife: Wife): Unit
  }

  case class HusbandsFriend(name: String) extends Friend(name) {
    override def visit(husband: Husband): Unit = println("Hey Buddy, " + husband.name)

    override def visit(wife: Wife): Unit = println("Hello Mrs. " + wife.name)
  }

  abstract class FamilyMember(name: String)
  case class Husband(name: String) extends FamilyMember(name)
  case class Wife(name: String) extends FamilyMember(name)

  val husband = Husband("Ritesh")
  val wife = Wife("Nita")
  val husbandsFriend = HusbandsFriend("Prakash")

  def greetEachOther(familyMember: FamilyMember, friend: Friend) = familyMember match {
    case h: Husband => friend.visit(h)
    case w: Wife => friend.visit(w)
    case _ => println("Hi")
  }

  greetEachOther(husband, husbandsFriend)
  greetEachOther(wife, husbandsFriend)
}

