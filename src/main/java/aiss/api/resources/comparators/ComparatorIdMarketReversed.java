package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Market;

public class ComparatorIdMarketReversed implements Comparator<Market> {
	
	@Override
	public int compare(Market o1, Market o2) {
		return o2.getId().compareTo(o1.getId());
	}

}
