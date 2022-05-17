package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Product;

public class ComparatorRatingProduct implements Comparator<Product>{

	@Override
	public int compare(Product o1, Product o2) {
		// TODO Auto-generated method stub
		return Integer.valueOf(o1.getRating()).compareTo(Integer.valueOf(o2.getRating()));
	}

}
