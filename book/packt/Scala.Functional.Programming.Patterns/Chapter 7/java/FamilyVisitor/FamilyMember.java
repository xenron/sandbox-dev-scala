
public abstract class FamilyMember {

	private String name;

	public FamilyMember(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public abstract void accept(Friend friend);
}
