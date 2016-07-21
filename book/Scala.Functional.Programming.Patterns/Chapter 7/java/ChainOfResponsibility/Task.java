
public class Task {
	private final String name;
	
	public String getName() {
		return name;
	}
	
	public Task(String name) {
		this.name = name;
	}

	private boolean containsOneOf(String... phraseList) {
		for (String phrase : phraseList) {
			if (contains(phrase)) {
				return true;
			}
		}
		return false;
	}	
	
	private boolean contains(String phrase) {
		return name.toLowerCase().contains(phrase);
	}

	public boolean needsHardLabor() {
		return containsOneOf("wood", "Fell", "hunt");
	}

	public boolean needsHouseHoldSkills() {
		return containsOneOf("sew", "cook");
	}

	public boolean isLightWeight() {
		return containsOneOf("dog", "cat", "playground");
	}
}
