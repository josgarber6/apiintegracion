package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Supermarket;

public class ComparatorNamePlaylist implements Comparator<Supermarket> {

	public int compare(Supermarket p1, Supermarket p2) {
		return p1.getName().compareTo(p2.getName());
	}

}
