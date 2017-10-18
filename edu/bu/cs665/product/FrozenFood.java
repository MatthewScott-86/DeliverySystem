package edu.bu.cs665.product;

public abstract class FrozenFood implements Deliverable {
	public Boolean isFrozen() {	return true; }
	public Boolean isWarm() { return false; }
	public abstract String getName();
}

