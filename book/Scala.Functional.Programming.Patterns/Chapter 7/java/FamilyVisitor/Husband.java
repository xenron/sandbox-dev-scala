
public class Husband extends FamilyMember {

	public Husband(String name) {
		super(name);
	}

	@Override
	public void accept(Friend friend) {
		friend.greetedBy(this);
	}

}
