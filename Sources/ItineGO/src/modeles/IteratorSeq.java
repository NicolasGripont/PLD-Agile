package modeles;

import java.util.Collection;
import java.util.Iterator;

public class IteratorSeq implements Iterator<Integer> {

	private final Integer[] candidats;
	private int nbCandidats;

	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * 
	 * @param nonVus
	 * @param sommetCrt
	 */
	public IteratorSeq(Collection<Integer> nonVus, int sommetCrt) {
		this.candidats = new Integer[nonVus.size()];
		this.nbCandidats = 0;
		for (Integer s : nonVus) {
			this.candidats[this.nbCandidats++] = s;
		}
	}

	@Override
	public boolean hasNext() {
		return this.nbCandidats > 0;
	}

	@Override
	public Integer next() {
		return this.candidats[--this.nbCandidats];
	}

	@Override
	public void remove() {
	}

}