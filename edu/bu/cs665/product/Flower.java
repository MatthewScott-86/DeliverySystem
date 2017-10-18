package edu.bu.cs665.product;

public abstract class Flower implements Deliverable {
	public Boolean isFrozen() {	return false; }
	public Boolean isWarm() { return false; }
	public abstract String getName();
}
