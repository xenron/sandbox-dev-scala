package com.packt.chapter01;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.Lists;

public class GroupThem {
	public List<List<Integer>> groupThem(final List<Integer> list) {
		final List<Integer> inputList = Collections.unmodifiableList(list);
		final List<List<Integer>> result = Lists.newArrayList();
		for (int i = 0; i < inputList.size();) {
			i = pickUpNextGroup(i, inputList, result); // i must progress
		}
		return result;
	}

	private int pickUpNextGroup(final int start, final List<Integer> inputList,
			final List<List<Integer>> result) {
		Validate.isTrue(!inputList.isEmpty(),
				"Input list should have at least one element");
		Validate.isTrue(start <= inputList.size(), "Invalid start index");

		final List<Integer> group = Lists.newArrayList();
		final int curr = start;
		int currElem = inputList.get(curr);
		group.add(currElem); // We will have at least one element in the group

		int next = start + 1; // next index may be out of range

		for (; next < inputList.size(); ++next) {
			final int nextElem = inputList.get(next); // next is in range
			if (nextElem - currElem == 1) { // grouping condition
				group.add(nextElem);
				currElem = nextElem; // setup for next iteration
			} else {
				break; // this group is done
			}
		}
		result.add(group); // add the group to result list
		Validate.isTrue(next > start); // make sure we keep moving
		return next; // next index to start iterating from
		              // could be past the last valid index
	}
}
