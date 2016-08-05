
public abstract class Friend {
	private String name;

	public Friend(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract void greetedBy(Husband husband);

	public abstract void greetedBy(Wife wife);
}
