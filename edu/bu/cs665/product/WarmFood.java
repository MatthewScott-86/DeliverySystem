package edu.bu.cs665.product;

public abstract class WarmFood implements Deliverable {
	public Boolean isFrozen() {	return false; }
	public Boolean isWarm() { return true; }
	public abstract String getName();
}
