package edu.bu.cs665.common;

import java.util.List;

import edu.bu.cs665.product.Deliverable;
import edu.bu.cs665.store.Store;


public class Order {
	private Customer customer;
	private List<Deliverable> products;
	private Store store;
	private int orderNumber;

	public Order(Customer cust, List<Deliverable> prods, Store srcStore, int number) {
		customer = cust;
		products = prods;
		store = srcStore;
		orderNumber = number;
	}
	public Store getStore() 				{ return store; }
	public Customer getCustomer() 			{ return customer; }
	public List<Deliverable> getProducts()	{ return products; }
	public int getOrderNumber() 			{ return orderNumber; }
}