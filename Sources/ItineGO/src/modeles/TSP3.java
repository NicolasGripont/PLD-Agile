package modeles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Troisième version du TSP, améliore l'itérateur à utiliser
 */
public class TSP3 extends TSP2 {

	@Override
	protected Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
		int[] coutNoeudCourent = new int[nonVus.size()];
		int i = 0;
		for (Integer s : nonVus) {
			coutNoeudCourent[i] = cout[sommetCrt][s];
			i++;
		}
		Integer[] TabNonVus = new Integer[nonVus.size()];
		i = 0;
		for (Integer s : nonVus) {
			TabNonVus[i] = s;
			i++;
		}
		this.tri_bulles(coutNoeudCourent, TabNonVus);
		List<Integer> CopieNonVus = new ArrayList<>();
		for (i = 0; i < TabNonVus.length; i++) {
			CopieNonVus.add(TabNonVus[i]);
		}
		return new IteratorSeq(CopieNonVus, sommetCrt);
	}

	void tri_bulles(int[] cout, Integer[] copieNonVus) {
		boolean ordre_cout_croissant = false;
		int taille = cout.length;
		while (!ordre_cout_croissant) {
			ordre_cout_croissant = true;
			for (int i = 0; i < (taille - 1); i++) {
				if (cout[i] > cout[i + 1]) {
					int tampon = cout[i];
					cout[i] = cout[i + 1];
					cout[i + 1] = tampon;
					tampon = copieNonVus[i];
					copieNonVus[i] = copieNonVus[i + 1];
					copieNonVus[i + 1] = tampon;
					ordre_cout_croissant = false;
				}
			}
			taille--;
		}
	}

}
