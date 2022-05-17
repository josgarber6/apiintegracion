package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Product;

public class ComparatorQuantityProductReversed implements Comparator<Product>{
	
	@Override
	public int compare(Product o1, Product o2) {
		return Integer.valueOf(o2.getQuantity()).compareTo(Integer.valueOf(o1.getQuantity()));
	}
}
