package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Product;

public class ComparatorArtistSong implements Comparator<Product> {
	
	public int compare(Product s1, Product s2) {
		return s1.getArtist().compareTo(s2.getArtist());
	}
	
}
