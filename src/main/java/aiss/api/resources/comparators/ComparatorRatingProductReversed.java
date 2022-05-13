package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Product;

public class ComparatorRatingProductReversed implements Comparator<Product> {
	
	@Override
	public int compare(Product o1, Product o2) {
		// TODO Auto-generated method stub
		return o2.getRating().compareTo(o1.getRating());
	}

}
