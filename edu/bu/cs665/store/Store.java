package edu.bu.cs665.store;

import java.util.ArrayList;
import java.util.List;

import edu.bu.cs665.common.Customer;
import edu.bu.cs665.common.Location;
import edu.bu.cs665.dispatch.*;
import edu.bu.cs665.product.Deliverable;
import edu.bu.cs665.product.ProductType;

public class Store {
	private ProductFactory factory;
	private BostonDispatcher dispatcher;
	private Location location;
	
	public Store (BostonDispatcher dispatch, ProductFactory fact, Location loc) {
		factory = fact;
		dispatcher = dispatch;
		location = loc;
	}
	
	public Location getLocation() { return location; }
	private BostonDispatcher getDispatcher() {	return dispatcher; }
	private ProductFactory getFactory() { return factory; }
	
	public void takeOrder(Customer cust, ProductType type) throws InterruptedException {
		List<Deliverable> products = new ArrayList<Deliverable>();
		
		products.add(getFactory().getProduct(type));
		//future use, if new product types are added
		//  stores may not be able to create anything
		//  of that type
		if (products.get(0) == null)
			products.remove(0);
		
		if (getDispatcher().isToday(cust.getBirthday())) {
			if (type != ProductType.Chocolate)
				products.add(getFactory().getProduct(ProductType.Chocolate));
			if (type != ProductType.Flowers)
				products.add(getFactory().getProduct(ProductType.Flowers));
		}
		getDispatcher().takeOrder(cust, products, this);
	}
}