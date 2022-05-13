package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Market;

public class ComparatorNameMarket implements Comparator<Market> {

	public int compare(Market p1, Market p2) {
		return p1.getName().compareTo(p2.getName());
	}

}
