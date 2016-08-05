
public abstract class FamilyMember {
	protected final FamilyMember next;

	public FamilyMember(final FamilyMember next) {
		super();
		this.next = next;
	}

	public abstract boolean canHandle(Task task);
	public abstract void handleIt(Task task);
	
	public void doSomeWork(Task task) {
		if (canHandle(task)) {
			handleIt(task);
		} else if (next != null) {
			next.doSomeWork(task);
		}
	}

}
