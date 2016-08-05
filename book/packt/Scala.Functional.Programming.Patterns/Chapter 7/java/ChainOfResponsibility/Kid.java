
public class Kid extends FamilyMember {

	public Kid(FamilyMember next) {
		super(next);
	}

	@Override
	public boolean canHandle(Task task) {
		return task.isLightWeight();
	}

	@Override
	public void handleIt(Task task) {
		System.out.println("Kid Handling: " + task.getName());
	}

}
