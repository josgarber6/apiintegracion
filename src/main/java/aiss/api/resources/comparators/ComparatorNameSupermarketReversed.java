package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Supermarket;

public class ComparatorNamePlaylistReversed implements Comparator<Supermarket> {

	public int compare(Supermarket p1, Supermarket p2) {
		return p2.getName().compareTo(p1.getName());
	}

}
