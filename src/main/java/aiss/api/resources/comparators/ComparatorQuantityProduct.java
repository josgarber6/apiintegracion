package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Product;

public class ComparatorQuantityProduct implements Comparator<Product>{

	@Override
	public int compare(Product o1, Product o2) {
		return Integer.valueOf(o1.getQuantity()).compareTo(Integer.valueOf(o2.getQuantity()));
	}

}
