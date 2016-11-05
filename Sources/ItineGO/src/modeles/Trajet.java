package modeles;

import java.util.List;

/**
 * Classe modélisant un trajet, c'est à dire la suite de tronçons à prendre pour relier deux noeuds
 */
public class Trajet {
	
	/**
	 * Liste des troncons
	 */
	private List<Troncon> troncons;

	/**
	 * Noeud de départ
	 */
	private Noeud depart;
	/**
	 * Noeud d'arrivée
	 */
	private Noeud arrive;
	
	public Trajet(Noeud depart, Noeud arrive, List<Troncon> troncons) {
		super();
		this.depart = depart;
		this.arrive = arrive;
		this.troncons = troncons;
	}

	public List<Troncon> getTroncons() {
		return troncons;
	}

	public Noeud getDepart() {
		return depart;
	}

	public Noeud getArrive() {
		return arrive;
	}

	@Override
	public String toString() {
		return "Trajet [troncons=" + troncons + ", depart=" + depart + ", arrive=" + arrive + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrive == null) ? 0 : arrive.hashCode());
		result = prime * result + ((depart == null) ? 0 : depart.hashCode());
		result = prime * result + ((troncons == null) ? 0 : troncons.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trajet other = (Trajet) obj;
		if (arrive == null) {
			if (other.arrive != null)
				return false;
		} else if (!arrive.equals(other.arrive))
			return false;
		if (depart == null) {
			if (other.depart != null)
				return false;
		} else if (!depart.equals(other.depart))
			return false;
		if (troncons == null) {
			if (other.troncons != null)
				return false;
		} else if (!troncons.equals(other.troncons))
			return false;
		return true;
	}
		
	public int getTemps() {
		int temps = 0;
		for(Troncon t : troncons) {
			temps += t.getLongueur()/t.getVitesse();
		}
		return temps;
	}
	
}
