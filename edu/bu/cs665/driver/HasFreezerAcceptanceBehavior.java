package edu.bu.cs665.driver;

import edu.bu.cs665.common.Order;
import edu.bu.cs665.dispatch.*;

public class HasFreezerAcceptanceBehavior implements OrderAcceptanceBehavior {
	public Boolean willDeliver(Order ord, BostonDispatcher dispatch) {
		return true;
	}
}
