package edu.bu.cs665.common;

import java.util.Random;

public class RandomNumberGenerator {
	private Random randomNo;
	public RandomNumberGenerator() {
		randomNo = new Random(123456789);
	}
	// creating random location of doubles in a grid of -5 to 5 representing miles
	public Location getNewLocation() {
		double x = (randomNo.nextInt(10000)-5000)/1000;
		double y = (randomNo.nextInt(10000)-5000)/1000;
		return new Location(x, y);
	}
}
