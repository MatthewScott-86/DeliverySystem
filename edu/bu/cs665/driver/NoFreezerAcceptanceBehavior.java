package edu.bu.cs665.driver;

import java.util.List;

import edu.bu.cs665.common.Customer;
import edu.bu.cs665.common.Order;
import edu.bu.cs665.dispatch.*;
import edu.bu.cs665.product.Deliverable;
import edu.bu.cs665.store.Store;

public class NoFreezerAcceptanceBehavior implements OrderAcceptanceBehavior {
	public Boolean willDeliver(Order ord, BostonDispatcher dispatch) {
		/* If any of the orders products are frozen
		 * 	if either it is rush hour or the distance > 2 miles
		 * 		refuse the order
		 */
		List<Deliverable> products = ord.getProducts();
		Customer cust = ord.getCustomer();
		Store store = ord.getStore();
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).isFrozen()) {
				if (dispatch.isRushHour() 
						|| cust.getLocation().calcDistance(store.getLocation()) > 2) {
					return false;
				}
			}		
		}
		return true;
	}
}
