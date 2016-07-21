package com.packt.chapter05;

public abstract class Animal {
	private Doctor doctor;

	public void getInoculated() {
		doctor.innoculate(this);
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
}
