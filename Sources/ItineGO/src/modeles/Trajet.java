package modeles;

import java.util.List;

/**
 * Classe modélisant un trajet, c'est à dire la suite de tronçons à prendre pour
 * relier deux noeuds
 */
public class Trajet {

	/**
	 * Liste des troncons
	 */
	private final List<Troncon> troncons;

	/**
	 * Noeud de départ
	 */
	private final Noeud depart;
	/**
	 * Noeud d'arrivée
	 */
	private final Noeud arrive;

	/**
	 * Constructeur de la classe
	 */
	public Trajet(Noeud depart, Noeud arrive, List<Troncon> troncons) {
		super();
		this.depart = depart;
		this.arrive = arrive;
		this.troncons = troncons;
	}

	public List<Troncon> getTroncons() {
		return this.troncons;
	}

	public Noeud getDepart() {
		return this.depart;
	}

	public Noeud getArrive() {
		return this.arrive;
	}

	@Override
	public String toString() {
		return "Trajet [troncons=" + this.troncons + ", depart=" + this.depart + ", arrive=" + this.arrive + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.arrive == null) ? 0 : this.arrive.hashCode());
		result = (prime * result) + ((this.depart == null) ? 0 : this.depart.hashCode());
		result = (prime * result) + ((this.troncons == null) ? 0 : this.troncons.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Trajet other = (Trajet) obj;
		if (this.arrive == null) {
			if (other.arrive != null) {
				return false;
			}
		} else if (!this.arrive.equals(other.arrive)) {
			return false;
		}
		if (this.depart == null) {
			if (other.depart != null) {
				return false;
			}
		} else if (!this.depart.equals(other.depart)) {
			return false;
		}
		if (this.troncons == null) {
			if (other.troncons != null) {
				return false;
			}
		} else if (!this.troncons.equals(other.troncons)) {
			return false;
		}
		return true;
	}

	public int getTemps() {
		int temps = 0;
		for (Troncon t : this.troncons) {
			temps += t.getLongueur() / t.getVitesse();
		}
		return temps;
	}

}
