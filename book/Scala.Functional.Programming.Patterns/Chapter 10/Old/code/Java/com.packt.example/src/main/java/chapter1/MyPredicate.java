package chapter1;

public class MyPredicate implements Predicate {

	public boolean apply(int nextElem, int currElem) {
		return nextElem - currElem == 1;
	}
}
