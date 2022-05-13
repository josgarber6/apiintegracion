package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Market;

public class ComparatorNameMarketReversed implements Comparator<Market> {

	public int compare(Market p1, Market p2) {
		return p2.getName().compareTo(p1.getName());
	}

}
