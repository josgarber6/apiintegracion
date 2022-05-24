package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Product;

public class ComparatorTypeProduct implements Comparator<Product> {

	@Override
	public int compare(Product o1, Product o2) {
		return o1.getType().compareTo(o2.getType());
	}

}
