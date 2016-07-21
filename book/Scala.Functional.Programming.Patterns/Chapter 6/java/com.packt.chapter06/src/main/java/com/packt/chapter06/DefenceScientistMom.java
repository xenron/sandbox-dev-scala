package com.packt.chapter06;

import java.util.List;

import com.google.common.collect.Lists;

public class DefenceScientistMom extends Mom {

	@Override
	protected List<String> mumsOfficeTaskList() {
		final List<String> dailyOfficeTasks = Lists.newArrayList(
				"This mum", "Will keep mum");
		return dailyOfficeTasks;
	}

}
