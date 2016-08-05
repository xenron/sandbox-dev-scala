package com.packt.chapter06;
import java.util.List;

import com.google.common.collect.Lists;

public class DoctorMom extends Mom {

	@Override
	protected final List<String> mumsOfficeTaskList() {
		final List<String> dailyOfficeTasks = Lists.newArrayList(
				"Get to the hospital", "Talk To Patients", "Perform Sugeries");
		return dailyOfficeTasks;
	}

}
