package com.packt.chapter01;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class GroupThemTest {

	private GroupThem groupThem;

	@Before
	public void init() {
		groupThem = new GroupThem();
	}

	@Test
	public void testFourGroup() {
		List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 9, 11, 20, 21, 22);
		List<List<Integer>> groups = groupThem.groupThem(list);
		assertThat(groups.size(), equalTo(4));
		assertThat(groups.get(0), contains(1, 2, 3, 4, 5));
		assertThat(groups.get(1), contains(9));
		assertThat(groups.get(2), contains(11));
		assertThat(groups.get(3), contains(20, 21, 22));
	}

}
