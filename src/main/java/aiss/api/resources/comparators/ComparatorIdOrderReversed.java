package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Order;

public class ComparatorIdOrderReversed implements Comparator<Order> {

	public int compare(Order o1, Order o2) {
		return o2.getId().compareTo(o1.getId());
	}

}
