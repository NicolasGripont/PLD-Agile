package modeles;

import java.util.ArrayList;
import java.util.Iterator;

import tsp.IteratorSeq;

public class TSP3 extends TSP2 {
	@Override
	protected Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
		return new IteratorMinFirst(nonVus, sommetCrt, cout);
	}
}
