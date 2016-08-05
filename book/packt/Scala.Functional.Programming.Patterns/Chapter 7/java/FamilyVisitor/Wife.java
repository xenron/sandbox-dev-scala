
public class Wife extends FamilyMember {

	public Wife(String name) {
		super(name);
	}

	@Override
	public void accept(Friend friend) {
		friend.greetedBy(this);
	}

}
