package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Product;

public class ComparatorPriceProduct implements Comparator<Product>{

	@Override
	public int compare(Product o1, Product o2) {
		return o1.getPrice().compareTo(o2.getPrice());
	}

}
