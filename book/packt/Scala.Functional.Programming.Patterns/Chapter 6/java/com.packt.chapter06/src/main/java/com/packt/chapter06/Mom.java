package com.packt.chapter06;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public abstract class Mom { 

	protected abstract List<String> mumsOfficeTaskList(); // 1

	public String getHerDailyRoutine() {
		final List<String> commonMumTasks = Lists.newArrayList(wakesUpEarly(),
				cooksFoodForKids(), feedKidsInEvening());
		final List<String> herOfficeTasks = mumsOfficeTaskList();

		return makeADailyRoutineMsg(commonMumTasks, herOfficeTasks);
	}

	private String makeADailyRoutineMsg(final List<String> commonMumTasks,
			final List<String> herOfficeTasks) { // 2
		final List<String> allTasks = Lists
				.newArrayList("---Daily Routine For a mum---");
		allTasks.addAll(commonMumTasks);
		allTasks.addAll(formatOfficeRoutine(herOfficeTasks));
		allTasks.add("---Thats all a mum can do in a day!---");

		final String allTasksMsgList = Joiner.on("\n").join(allTasks);
		return allTasksMsgList;
	}

	private List<String> formatOfficeRoutine(final List<String> workMsg) {
		final List<String> formattedMsgList = Lists
				.newArrayList("My office routine");
		for (final String msg : workMsg) {
			formattedMsgList.add("\t" + msg);
		}
		formattedMsgList.add("No more shop talk");

		return formattedMsgList;
	}

	private String feedKidsInEvening() {
		return "Feeding kids in the evening";
	}

	private String cooksFoodForKids() {
		return "Cooking Food for my kids";
	}

	private String wakesUpEarly() {
		return "Sprightly mum I am - waking up!!!";
	}
}
