package edu.bu.cs665.store;

import edu.bu.cs665.product.*;

public class FancyRestaurantFactory implements ProductFactory{
	
	public Deliverable getProduct(ProductType type) {
		switch(type) {
			case Chocolate:
				return new BlackForestGateau();
			case Food:
				return new ShepardsPie();
			case Flowers:
				return new RoseBouquet();
			default:
				return null;
		}
	}
}