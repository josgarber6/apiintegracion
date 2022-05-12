package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Order;

public class ComparatorIdOrder implements Comparator<Order> {

	public int compare(Order o1, Order o2) {
		return o1.getId().compareTo(o2.getId());
	}

}
