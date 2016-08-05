package com.packt.chapter01;

import java.util.ArrayList;
import java.util.List;

public class TaskDriver {
	public static void main(String[] args) throws InterruptedException {
		List<Integer> list = new ArrayList<Integer>();
		list.add(10);
		list.add(11);
		list.add(12);
		list.add(13);
		
		ListSumTask task = new ListSumTask(list);
		Thread t = new Thread(task);
		t.start();
		t.join();

		System.out.println("task computed sum = " + task.getAcc());
	}
}
