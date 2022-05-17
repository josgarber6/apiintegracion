package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Product;

public class ComparatorPriceProduct implements Comparator<Product>{

	@Override
	public int compare(Product o1, Product o2) {
		return Integer.valueOf(o1.getPrice()).compareTo(Integer.valueOf(o2.getPrice()));
	}

}
