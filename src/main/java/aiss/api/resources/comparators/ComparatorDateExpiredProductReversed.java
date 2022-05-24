package aiss.api.resources.comparators;

import java.time.LocalDate;
import java.util.Comparator;

import aiss.model.Product;

public class ComparatorDateExpiredProductReversed implements Comparator<Product> {
	
	@Override
	public int compare(Product o1, Product o2) {
		return LocalDate.parse(o2.getExpirationDate()).compareTo(LocalDate.parse(o1.getExpirationDate()));
	}
}
