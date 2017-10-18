package edu.bu.cs665.common;

import java.util.Calendar;
import java.util.Date;

public class Customer {
	private Location location;
	private int birthDayOfYear;
	
	public Customer(Date birthday, Location loc)	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(birthday);
		birthDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		location = loc;
	}	
	
	public Location getLocation() {	return location; }
	public int getBirthday() {	return birthDayOfYear; }	
	public void receiveDeliveryMessage(String message) {
		System.out.println(message);
	}
}
