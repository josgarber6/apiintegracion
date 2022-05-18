package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Product;

public class ComparatorPriceProductReversed implements Comparator<Product>{
	
	@Override
	public int compare(Product o1, Product o2) {
		return Double.valueOf(o2.getPrice()).compareTo(Double.valueOf(o1.getPrice()));
	}

}
