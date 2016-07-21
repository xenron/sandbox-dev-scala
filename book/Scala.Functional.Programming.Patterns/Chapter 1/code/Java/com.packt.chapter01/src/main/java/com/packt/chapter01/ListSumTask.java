package com.packt.chapter01;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListSumTask implements Runnable {

	private final List<Integer> list;
	private volatile int acc;
	
	public ListSumTask(List<Integer> list) {
		super();
		this.list = list;
		this.acc = 0;
	}

	public void run() {
		sleepFor(4);
		for (Integer i : list) {
			acc += i;
		}
	}

	private void sleepFor(long secs) {
		try {
			TimeUnit.SECONDS.sleep(secs);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getAcc() {
		return acc;
	}
}
