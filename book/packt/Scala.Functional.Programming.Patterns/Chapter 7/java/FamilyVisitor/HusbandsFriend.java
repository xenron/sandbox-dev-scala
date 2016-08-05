
public class HusbandsFriend extends Friend {

	public HusbandsFriend(String name) {
		super(name);
	}

	@Override
	public void greetedBy(Husband husband) {
		System.out.println("Hey budd, " + husband.getName() + ", how is life old man!!!"); // and lots of 
																						   // back slapping etc. etc.
	}

	@Override
	public void greetedBy(Wife wife) {
		System.out.println("How do you do, Mrs. " + wife.getName());
	}

}
