package com.packt.chapter05;

public class Donkey implements Walks, GoodsMover, NameIt {
	public void walk() {
		ProcessIt.walk(this);
	}
	public void moveGoods() {
		ProcessIt.moveGoods(this);
	}
	public String name() {
		return "Donkey";
	}
}

