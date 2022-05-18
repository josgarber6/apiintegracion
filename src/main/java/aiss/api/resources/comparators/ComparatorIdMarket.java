package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Market;

public class ComparatorIdMarket implements Comparator<Market> {

	@Override
	public int compare(Market o1, Market o2) {
		return o1.getId().compareTo(o2.getId());
	}

}
