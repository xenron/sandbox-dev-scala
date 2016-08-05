
public class Mom extends FamilyMember {

	public Mom(FamilyMember next) {
		super(next);
	}

	@Override
	public boolean canHandle(Task task) {
		return task.needsHouseHoldSkills();
	}

	@Override
	public void handleIt(Task task) {
		System.out.println("Mom Handling: " + task.getName());		
	}

}
