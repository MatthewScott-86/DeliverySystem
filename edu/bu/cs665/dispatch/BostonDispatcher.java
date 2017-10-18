package edu.bu.cs665.dispatch;

/* Dispatchers responsible for letting drivers know orders are available,
 * taking orders from stores, giving them up to drivers,
 * and letting drivers know drive time, 
 * todays date, and if its rush hour
 * */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.bu.cs665.common.Customer;
import edu.bu.cs665.common.Location;
import edu.bu.cs665.common.Order;
import edu.bu.cs665.driver.Driver;
import edu.bu.cs665.product.Deliverable;
import edu.bu.cs665.store.Store;

public class BostonDispatcher {
	private List<Driver> drivers;
	private volatile List<Order> orders;
	private int todaysDayOfYear;
	private Boolean rushHour;
	private double maxDistance;
	private int orderNumber;
	
	public BostonDispatcher(Date todaysDate, Boolean isRushHour) {
		drivers = new ArrayList<Driver>();
		orders = new ArrayList<Order>();
		Calendar date = Calendar.getInstance();
		date.setTime(todaysDate);
		todaysDayOfYear = date.get(Calendar.DAY_OF_YEAR);
		rushHour = isRushHour;
		maxDistance = 10;
		orderNumber = 0;
	}
	
	public Boolean hasOrders() { return orders.size() > 0; }
	
	/* Observer pattern responsibilities */
	public void registerDriver(Driver driver){
		drivers.add(driver);
	}
	
	public void removeDriver(Driver driver) {
		int index = drivers.indexOf(driver);
		if (index >= 0)
			drivers.remove(index);
	}
	public void notifyDrivers(Boolean deliveryAvailable) {
		
		for (int i = 0; i < drivers.size(); i++)
		{
			Driver driver = (Driver)drivers.get(i);
			driver.setDispatcherHasOrder(deliveryAvailable);
		}
	}
	
	public void notifyDrivers(Order ord) {

		for (int i = 0; i < drivers.size(); i++) {
			Driver driver = drivers.get(i);
			Boolean willDeliver = driver.willDeliver(ord);
			double distance = driver.getLocation().calcDistance(ord.getStore().getLocation());
			
			if (willDeliver && distance < maxDistance) {
				driver.setDispatcherHasOrder(true);
			}			
		}
	}
	
	/* Orders come from stores */
	public void takeOrder(Customer cust, List<Deliverable> product, Store store) throws InterruptedException {
		Order order = new Order(cust, product, store, orderNumber);
		orders.add(order);
		List<Deliverable> products = order.getProducts();
		
		String productsNames = products.get(0).getName();
		for (int i = 1; i < products.size(); i++)
			productsNames += ", " + products.get(i).getName();
		System.out.println("Order Number:" + orderNumber + " up for delivery " + productsNames);

		orderNumber++;
		notifyDrivers(order);
	}
	
	/* Driver requests order, must be synchronized */
	public synchronized Order giveUpOrder(Driver driver) {
		for (int i = 0; i < orders.size(); i++)
		{
			Order order = orders.get(i);
			if (order != null) {
				Location driverLocation = driver.getLocation();
				Store store = order.getStore();
				Location storeLocation = store.getLocation();
				double driverStoreDistance = driverLocation.calcDistance(storeLocation);
				
				if (driver.willDeliver(order) && driverStoreDistance < maxDistance)
				{
					orders.remove(i);
					if (orders.size() == 0)
						notifyDrivers(false);
					return order;
				}
			}
		}
		return null;
	}
	
	/* Clean up Driver Threads */
	public void finalize() throws InterruptedException {
		for (int i = 0; i < drivers.size(); i++) {
			Driver driver = (Driver)drivers.get(i);
			
			//drivers are in loop over this variable == true
			driver.setLiveCity(false);
			driver.join();
		}
	}
	
	/* Informational functions */
	public long getDriveTime(Location loc1, Location loc2) {
		// 1 second per mile, pretty quick, eh?
		return (long)loc1.calcDistance(loc2) * 1000;
	}
	
	public Boolean isToday(int otherDayOfYear) {
		if (todaysDayOfYear == otherDayOfYear)
			return true;
		return false;
	}
	
	public Boolean isRushHour() {
		return rushHour;
	}
}
