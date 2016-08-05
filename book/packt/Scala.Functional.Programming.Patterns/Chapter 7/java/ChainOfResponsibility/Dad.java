
public class Dad extends FamilyMember {

	public Dad(FamilyMember next) {
		super(next);
	}

	@Override
	public boolean canHandle(Task task) {
		return task.needsHardLabor();
	}

	@Override
	public void handleIt(Task task) {
		System.out.println("Dad Handling: " + task.getName());
	}

}
