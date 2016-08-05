package com.packt.chapter01;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

class Record implements Comparable<Record> {
	private final int num;
	private final String str;

	public Record(int num, String str) {
		super();
		this.num = num;
		this.str = str;
	}

	public int getNum() {
		return num;
	}

	public String getStr() {
		return str;
	}

	public int compareTo(Record o) {
		if (this.num > o.num) {
			return 1;
		} else if (this.num < o.num) {
			return -1;
		}
		return 0;
	}
}

public class SortNumbers {
	public static void main(String[] args) {
		List<Record> list = Lists.newArrayList(new Record(9, "zeroth"), new Record(4, "first"),
				new Record(11, "second"), new Record(3, "third"), new Record(22, "fourth"));
		Collections.sort(list);
		
		for(Record r: list) {
			System.out.println(r.getNum() + " , " + r.getStr());
		}
	}
}
