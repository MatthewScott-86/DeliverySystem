package test.java;

import static org.junit.Assert.*;

import java.text.ParseException;
import org.junit.Test;

import edu.bu.cs665.city.Boston;

public class Integration {
	@Test
	public void testDefault() throws InterruptedException, ParseException {
		
		Boston.main(null);
	}

	@Test
	public void testMain() throws InterruptedException, ParseException {
	
		int returnCode = Boston.Run(10,2,10,true,"1986-08-25");
		assert(returnCode == 1);
	}
	
	@Test
	public void test2() throws InterruptedException, ParseException {

		int returnCode = Boston.Run(2,0,2,true,"1986-02-25");
		assert(returnCode == 1);
	}
	
	@Test
	public void test3() throws InterruptedException, ParseException {

		int returnCode = Boston.Run(20,10,10,true,"1986-02-25");
		assert(returnCode == 1);
	}
	
	@Test
	public void test4() throws InterruptedException, ParseException {
		
		int returnCode = Boston.Run(10,5,20,true,"1986-02-25");
		assert(returnCode == 1);
	}
	
	@Test
	public void test5() throws InterruptedException, ParseException {


		int returnCode = Boston.Run(30,5,20,true,"1986-02-25");
		assert(returnCode == 0);
	}

	@Test
	public void test6() throws InterruptedException, ParseException {
		
		int returnCode = Boston.Run(10,5,30,true,"1986-02-25");
		assert(returnCode == 1);
	}

}
