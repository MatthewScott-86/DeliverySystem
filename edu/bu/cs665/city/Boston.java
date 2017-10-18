package edu.bu.cs665.city;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import edu.bu.cs665.common.*;
import edu.bu.cs665.dispatch.*;
import edu.bu.cs665.driver.*;
import edu.bu.cs665.product.ProductType;
import edu.bu.cs665.store.*;
/* Main function
 * Responsible for 
 * 	Creating main objects
 * 	Pairing drivers with dispatcher (constructor observer pattern)
 * 	Pairing stores with dispatcher (constructor)
 */
public class Boston {
	public static void main(String args[]) throws InterruptedException, ParseException {
		int numDrivers = 2;
		int numDriversWithFreezer = 0;
		int numCustomers = 2;
		int numStores = 5;
		Boolean rushHour = false;
		String dateString = "1986-08-25";
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		RandomNumberGenerator randomNoGen = new RandomNumberGenerator();
		
		if(args != null && args.length > 4) {
			
			numDrivers = Integer.parseInt(args[0]);
			numDriversWithFreezer = Integer.parseInt(args[1]);
			numCustomers = Integer.parseInt(args[2]);
			rushHour = Boolean.parseBoolean(args[3]);
			dateString = args[4];
			
		}
		else {
			System.out.println("Not enough arguments, running default program");
		}
			
		Run(numDrivers, numDriversWithFreezer, numCustomers, rushHour, dateString);	
		
	}
	public static int Run(int numDrivers, int numDriversWithFreezer, int numCustomers, Boolean rushHour, String dateString) throws ParseException, InterruptedException {

		if (numDrivers > 20) {
			System.out.println("Exceeds maximum number of drivers");
			System.out.println("");
			return 0;
		}
		int numStores = 5;
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		RandomNumberGenerator randomNoGen = new RandomNumberGenerator();
		
		Date date;
		try {
			date = (Date)fmt.parse(dateString);
		}
		catch (ParseException e){
			dateString = "1986-08-25";
			date = (Date)fmt.parse(dateString);
			System.out.println("Failed to parse date, should be yyyy-MM-dd");
			System.out.println("Using default 1986-08-25");
		}
		
		BostonDispatcher dispatcher = new BostonDispatcher(date, rushHour);
		List<Driver> drivers = new ArrayList<Driver>();
		List<Customer> customers = new ArrayList<Customer>();
		List<Store> stores = new ArrayList<Store>();
		
		for (int i = 0; i < numDrivers; i++) {
			if (i < numDriversWithFreezer)
				drivers.add(new Driver("Driver " + (i+1), dispatcher, randomNoGen.getNewLocation(), new HasFreezerAcceptanceBehavior()));
			else 
				drivers.add(new Driver("Driver " + (i+1), dispatcher, randomNoGen.getNewLocation(), new NoFreezerAcceptanceBehavior()));
		}
		
		for (int i = 0; i < numCustomers; i++) {
			if (i%12 > 8) {
				customers.add(new Customer(fmt.parse("1986-" + (i%12 + 1) + "-25"), randomNoGen.getNewLocation()));
			}
			else {
				customers.add(new Customer(fmt.parse("1986-0" + (i%12 + 1) + "-25"), randomNoGen.getNewLocation()));
			}
		}
		
		stores.add(new Store(dispatcher, new IceCreamStoreFactory(), randomNoGen.getNewLocation()));
		stores.add(new Store(dispatcher, new BirthdayStoreFactory(), randomNoGen.getNewLocation()));
		stores.add(new Store(dispatcher, new BJsStoreFactory(), randomNoGen.getNewLocation()));
		stores.add(new Store(dispatcher, new FancyRestaurantFactory(), randomNoGen.getNewLocation()));
		stores.add(new Store(dispatcher, new OnlineRetailerFactory(), randomNoGen.getNewLocation()));
		
		for (int i = 0; i < numCustomers; i++) {
			int storeNumber = i % numStores;
			Store localStore = stores.get(storeNumber);
			Customer cust = customers.get(i);
			localStore.takeOrder(cust, ProductType.values()[i % ProductType.count.ordinal()]);
		}
		
		// Give drivers time to pick up all orders
		int tryCount = 0;
		while (dispatcher.hasOrders() && tryCount < 5) {
			tryCount++;
			Thread.sleep(2000);
		}
		dispatcher.finalize();
		
		if (dispatcher.hasOrders()) {
			System.out.println("Not all orders delivered!");
			System.out.println("");
			return 0;
		}
		else {
			System.out.println("All orders Delivered!");
			System.out.println("");
			return 1;
		}
	}
}
