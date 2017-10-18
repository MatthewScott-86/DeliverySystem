package edu.bu.cs665.store;

import edu.bu.cs665.product.*;

public class BJsStoreFactory implements ProductFactory{
	
	public Deliverable getProduct(ProductType type) {
		switch(type) {
			case Chocolate:
				return new KingSizeMilkyWay();
			case Food:
				return new SalisburySteakFrozenMeal();
			case Flowers:
				return new MixedBouquet();
			default:
				return null;
		}
	}
}
