
public class Driver {
	public static void main(String[] args) {
		HusbandsFriend husbandsFriend = new HusbandsFriend("Rakesh");
		Husband husband = new Husband("Prakash");
		Wife wife = new Wife("Neeta");

		greetEachOther(husbandsFriend, wife);
		greetEachOther(husbandsFriend, husband);
	}

	private static void greetEachOther(Friend friend, FamilyMember familyMember) {
		familyMember.accept(friend);
	}
}
