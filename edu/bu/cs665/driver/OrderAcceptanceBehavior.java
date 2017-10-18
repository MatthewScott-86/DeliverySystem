package edu.bu.cs665.driver;

import edu.bu.cs665.common.Order;
import edu.bu.cs665.dispatch.*;

public interface OrderAcceptanceBehavior {
	public Boolean willDeliver(Order order, BostonDispatcher dispatch);
}

