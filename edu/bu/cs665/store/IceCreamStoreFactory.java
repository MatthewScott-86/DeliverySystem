package edu.bu.cs665.store;

import edu.bu.cs665.product.*;

public class IceCreamStoreFactory implements ProductFactory{
	
	public Deliverable getProduct(ProductType type) {
		switch(type) {
		case Chocolate:
			return new KingSizeMilkyWay();
		case Flowers:
			return new Dandelion();
		case Food:
			return new IceCream();
		default:
			return null;
			
		}
	}
}
