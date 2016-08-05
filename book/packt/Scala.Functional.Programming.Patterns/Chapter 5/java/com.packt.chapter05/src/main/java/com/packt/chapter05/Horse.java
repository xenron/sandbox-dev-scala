package com.packt.chapter05;

public class Horse extends Animal implements Walks, NameIt {
	public void walk() {
		ProcessIt.walk(this);
	}
	public String name() {
		return "Horse";
	}
}

