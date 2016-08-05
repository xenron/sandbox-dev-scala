package chapter03;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ParserTest {

	@Test
	public void test1() {
		Parser parser = new Parser("(1+2)*3*(2+4)");
		assertThat(parser.expr(), equalTo(54));
	}

	@Test
	public void test2() {
		Parser parser = new Parser("((11+22)*333*(22+14))");
		assertThat(parser.expr(), equalTo(395604));
	}

	@Test
	public void test3() {
		Parser parser = new Parser("(1+2)*3+4");
		assertThat(parser.expr(), equalTo(13));
	}
}
