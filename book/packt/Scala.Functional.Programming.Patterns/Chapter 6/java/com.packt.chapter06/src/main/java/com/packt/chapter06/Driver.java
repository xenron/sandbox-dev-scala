package com.packt.chapter06;

public class Driver {
	public static void main(String[] args) {
		Mom mom = new DoctorMom();
		System.out.println(mom.getHerDailyRoutine());
	}

}
