package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Product;

public class ComparatorTypeProductReversed implements Comparator<Product> {
	
	@Override
	public int compare(Product o1, Product o2) {
		return o2.getType().compareTo(o1.getType());
	}

}
