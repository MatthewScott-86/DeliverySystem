package edu.bu.cs665.common;

import edu.bu.cs665.common.Location;

public class Location {
	private double x;
	private double y;
	public Location(double inX, double inY) {
		x = inX;
		y = inY;
	}
	public double calcDistance(Location other) {
		return Math.sqrt(
				Math.pow(other.x - x, 2) +
				Math.pow(other.y - y, 2)
				);
	}
}