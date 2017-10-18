package edu.bu.cs665.driver;

import java.util.List;

import edu.bu.cs665.common.*;
import edu.bu.cs665.dispatch.*;
import edu.bu.cs665.product.Deliverable;

public class Driver extends Thread {
	private volatile Boolean dispatcherHasOrder;
	private volatile Boolean liveCity;
	private Boolean onDelivery;
	private BostonDispatcher dispatcher;
	private Order currentOrder;
	private String driverName;
	private Location location;
	private OrderAcceptanceBehavior acceptanceBehavior;
	
	public Driver(String name, BostonDispatcher dispatch, Location loc, OrderAcceptanceBehavior behavior) {
		driverName = name;
		location = loc;
		dispatcher = dispatch;
		dispatcherHasOrder = false;
		onDelivery = false;
		liveCity = true;
		acceptanceBehavior = behavior;
		//register is not thread safe
		//	always execute before start
		dispatch.registerDriver(this);
		start();
	}
	
	/* Main function runs until dispatcher calls finalize
	 * setting liveCity to false
	 *  */
	public void run() {
		while (liveCity) {
			if (getDispatcherHasOrder()) {
				Order order = dispatcher.giveUpOrder(this);
				
				if (order != null) {
					currentOrder = order;
					onDelivery = true;
					deliver();
					setLocation(order.getCustomer().getLocation());
					onDelivery = false;
					currentOrder = null;
				}
			}
		}
	}
	private int getOrderNumber()			{ return currentOrder.getOrderNumber(); }
	private String getDriverName() 			{ return driverName; }	
	private void setLocation(Location loc) 	{ location = loc; }
	public Location getLocation() 			{ return location; }
	public Boolean getDispatcherHasOrder() 	{ return dispatcherHasOrder; }
	public void setLiveCity(Boolean val) 	{ liveCity = val; }
	public Boolean getOnDelivery() 			{ return onDelivery; }
	public void setDispatcherHasOrder(Boolean dispatcherOrder) { dispatcherHasOrder = dispatcherOrder; }
	
	public Boolean willDeliver(Order ord) 	{ 
		return acceptanceBehavior.willDeliver(
				ord, 
				dispatcher); 
	}
	
	public String getProductName() { 
		List<Deliverable> products = currentOrder.getProducts();
		Deliverable prod = products.get(0);
		String productName = prod.getName();
		productName += prod.isFrozen() ? " (In the Freezer)" : "";
		productName += prod.isWarm() ? " (In a Warming Bag)" : "";
		
		for (int i = 1; i < products.size(); i++) {
			prod = products.get(i);
			productName += ", " + prod.getName();
			productName += prod.isFrozen() ? " (In the Freezer)" : "";
			productName += prod.isWarm() ? " (In a Warming Bag)" : "";
		}
		return productName;
	}
	
	public long getDriveTime() {
		return dispatcher.getDriveTime(getLocation(), 
				currentOrder.getCustomer().getLocation());
	}	
	
	public void deliver() {
		currentOrder.getCustomer().receiveDeliveryMessage(
				"Order Number:" + 
				getOrderNumber() +
				" being delivered by " +
				getDriverName() + 
				" Order:" +
				getProductName());
		try {
			Thread.sleep(getDriveTime());
		}
		catch(Exception except) {
			System.out.println(except.getMessage());
		}
		finally {
			currentOrder.getCustomer().receiveDeliveryMessage(
					"Order Number:" + 
					getOrderNumber() +
					" was delivered by " +
					getDriverName() + 
					" Order:" +
					getProductName());
		}
	}
}
