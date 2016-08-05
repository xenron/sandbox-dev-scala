public class Driver {
	public static void main(String[] args) {
		FamilyMember familyMember = new Dad(new Mom(new Kid(null)));		
		familyMember.doSomeWork(new Task("Need to sew up a shirt button"));
		familyMember.doSomeWork(new Task("Walk the Dog"));
		familyMember.doSomeWork(new Task("cut some firewood"));
		familyMember.doSomeWork(new Task("feed the cat"));
	}
}
