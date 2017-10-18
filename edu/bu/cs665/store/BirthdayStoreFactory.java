package edu.bu.cs665.store;

import edu.bu.cs665.product.*;

public class BirthdayStoreFactory implements ProductFactory{
	
	public Deliverable getProduct(ProductType type) {
		switch(type) {
		case Chocolate:
			return new HeartShapedBoxOfChocolate();
		case Food:
			return new BirthdayCake();
		case Flowers:
			return new RoseBouquet();
		default:
			return null;
		}
		
	}
}