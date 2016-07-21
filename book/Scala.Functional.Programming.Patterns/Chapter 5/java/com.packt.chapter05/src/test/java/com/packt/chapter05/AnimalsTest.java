package com.packt.chapter05;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class AnimalsTest {
	@Test
	public void testInnoculation() {
		Doctor doctor = mock(Doctor.class); // 1
		Animal animal = new Horse();
		animal.setDoctor(doctor); // 2
		animal.getInoculated();
		
		verify(doctor).innoculate(animal); // 3
	}
}
