package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Product;

public class ComparatorIdProduct implements Comparator<Product> {
	
	public int compare(Product s1, Product s2) {
		return s1.getId().compareTo(s2.getId());
	}
	
}
