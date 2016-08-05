package com.packt.example;

import java.util.ArrayList;
import java.util.List;

public class Wrapper {
	private final List<Integer> value;
	
	public Wrapper() {
		value = new ArrayList<Integer>();
		//...
	}
	
	public List<Integer> getValue() {
		return value;
	}
	
}
