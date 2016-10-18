package modeles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import utility.MapUtil;

public class IteratorMinFirst implements Iterator<Integer> {

	private Integer[] candidats;
	private int nbCandidats;

	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * @param nonVus
	 * @param sommetCrt
	 */
	public IteratorMinFirst(Collection<Integer> nonVus, int sommetCrt, int[][] cout){
		//nombre de sommets pouvant être atteints
		nbCandidats = 0;
		//strtucture pour stocker chaque sommet avec son cout
		Map<Integer, Integer> coutCandidats = new HashMap<Integer, Integer>();
		//on ajoute les sommets
		for (Integer s : nonVus){
			coutCandidats.put(s, cout[sommetCrt][s]);
			nbCandidats++;
		}
		//On trie
		LinkedHashMap<Integer, Integer> coutCandidatsSorted = MapUtil.sortByValue(coutCandidats);
		//On renvoie les sommets triés par cout croissant
		int i = 0;
		for(Integer sommet : coutCandidatsSorted.keySet()) {
			this.candidats[i++] = sommet;
		}
	}
	
	@Override
	public boolean hasNext() {
		return nbCandidats > 0;
	}

	@Override
	public Integer next() {
		return candidats[--nbCandidats];
	}

	@Override
	public void remove() {}

}
