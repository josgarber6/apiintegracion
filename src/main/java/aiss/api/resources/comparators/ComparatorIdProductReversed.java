package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Product;

public class ComparatorIdProductReversed implements Comparator<Product> {
	
	public int compare(Product s1, Product s2) {
		return s2.getId().compareTo(s1.getId());
	}

}
