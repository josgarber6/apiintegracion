package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Product;

public class ComparatorNameProduct implements Comparator<Product> {

	public int compare(Product s1, Product s2) {
		return s1.getName().compareTo(s2.getName());
	}

}
