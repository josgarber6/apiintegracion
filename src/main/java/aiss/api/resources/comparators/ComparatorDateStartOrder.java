package aiss.api.resources.comparators;

import java.time.LocalDate;
import java.util.Comparator;

import aiss.model.Order;

public class ComparatorDateStartOrder implements Comparator<Order> {

	@Override
	public int compare(Order o1, Order o2) {
		return LocalDate.parse(o1.getStartDate()).compareTo(LocalDate.parse(o2.getStartDate()));
	}
	
}
