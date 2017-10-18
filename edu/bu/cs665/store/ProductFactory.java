package edu.bu.cs665.store;

import edu.bu.cs665.product.Deliverable;
import edu.bu.cs665.product.ProductType;

public interface ProductFactory {
	public Deliverable getProduct(ProductType type);
}
