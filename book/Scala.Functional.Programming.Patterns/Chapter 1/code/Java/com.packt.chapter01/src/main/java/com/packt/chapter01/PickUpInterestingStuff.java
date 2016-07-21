package com.packt.chapter01;

import java.util.List;

import com.google.common.collect.Lists;

public class PickUpInterestingStuff {
	public static void main(String[] args) {
		List<Books> listOfBooks = Lists.newArrayList();
		listOfBooks.add(new Books("Carr", Lists.newArrayList("The Mad Hatter Mystery",
				  "The Blind Barber")));
		listOfBooks.add(new Books("Christie", Lists.newArrayList("Death On The Nile",
				  "Murder in Mesopotamia")));
		
		List<String> titlesFor = Lists.newArrayList();
		
		for(Books books: listOfBooks) {
			if (books.getAuthor().equals("Carr")) {
				titlesFor.addAll(books.getTitles());
			}
		}
		System.out.println(titlesFor);
	}
}
