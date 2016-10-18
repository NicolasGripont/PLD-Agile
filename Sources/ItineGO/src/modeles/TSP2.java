<<<<<<< HEAD
package modeles;

import java.util.ArrayList;

import tsp.TSP1;

public class TSP2 extends TSP1 {
	@Override
	protected int bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
		int min = 100000;
		for(int i = 0; i < nonVus.size(); ++i) {
			if(cout[sommetCourant][nonVus.get(i)] < min) {
				min = cout[sommetCourant][nonVus.get(i)];
			}
		}
		return min;
	}
}
=======
package modeles;

import java.util.ArrayList;

import tsp.TSP1;

public class TSP2 extends TSP1 {
	@Override
	protected int bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
		int min = 100000;
		for(int i = 0; i < nonVus.size(); ++i) {
			if(cout[sommetCourant][nonVus.get(i)] < min) {
				min = cout[sommetCourant][nonVus.get(i)];
			}
		}
		return min;
	}
}
>>>>>>> 335a03cec251d66d9fe2fc594d74504cd15fddb9
