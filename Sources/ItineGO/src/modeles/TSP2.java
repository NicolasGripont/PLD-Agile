package modeles;

import java.util.ArrayList;

/**
 * Deuxième version du TSP, améliore la fonction pour la borne à tester
 */
public class TSP2 extends TSP1 {
	@Override
	protected int bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
		int min = 0;
		for (int i : nonVus) {
			int mini = Integer.MAX_VALUE;
			for (int j : nonVus) {
				if ((cout[i][j] < mini) && (i != j)) {
					mini = cout[i][j];
				}
			}
			if (cout[i][0] < mini) {
				mini = cout[i][0];
			}
			min += mini;
		}
		int minj = Integer.MAX_VALUE;
		for (int j : nonVus) {
			if (cout[sommetCourant][j] < minj) {
				minj = cout[sommetCourant][j];
			}
		}
		for (int j : nonVus) {
			min += duree[j];
		}
		min += minj;
		return min;
	}
}
