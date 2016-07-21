package chapter2;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class NodeTest {

	@Test
	public void testTraversal() {
		Node tree = Node.createTreeWithRoot(72);
		tree.insert(50);
		tree.insert(25);
		tree.insert(65);
		tree.insert(95);
		tree.insert(88);
		tree.insert(112);

		List<Integer> list = Lists.newArrayList();

		tree.traverseInOrder(list);

		assertThat(list, contains(25, 50, 65, 72, 88, 95, 112));
	}

}
