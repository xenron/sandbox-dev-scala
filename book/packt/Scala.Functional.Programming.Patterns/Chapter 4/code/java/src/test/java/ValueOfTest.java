package chap04;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValueOfTest {

	@Test
	public void memoizationTest() {
		Integer a1 = Integer.valueOf(12);
		Integer a2 = Integer.valueOf(12);
	
		Integer a3 = Integer.valueOf(12112);
		Integer a4 = Integer.valueOf(12112);
	
		assertSame(a1, a2);
		assertNotSame(a3, a4);
	}

}
