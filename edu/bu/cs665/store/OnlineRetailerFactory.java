package edu.bu.cs665.store;


import edu.bu.cs665.product.*;
public class OnlineRetailerFactory implements ProductFactory{
	
	public Deliverable getProduct(ProductType type) {
		switch(type) {
			case Chocolate:
				return new KingSizeMilkyWay();
			case Food:
				return new FrozenMakeYourOwnMeal();
			case Flowers:
				return new MixedBouquet();
			default:
				return null;
		}
	}
}