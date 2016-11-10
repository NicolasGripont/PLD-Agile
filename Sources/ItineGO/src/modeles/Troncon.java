package modeles;

/**
 * Classe modélisant un tronçon, c'est un lien orienté qui relie directement
 * deux noeuds
 */
public class Troncon {
	/**
	 * Nom de la rue, plusieurs tronçons peuvent appartenir à la même rue
	 */
	private String nomRue;
	/**
	 * Longueur du tronçon
	 */
	private int longueur;
	/**
	 * Vitesse à laquelle on peut parcourir le tronçon
	 */
	private int vitesse;
	/**
	 * Noeud d'origine du tronçon
	 */
	private Noeud origine;
	/**
	 * Noeud de destination du tronçon
	 */
	private Noeud destination;

	/**
	 * Constructeur de la classe
	 */
	public Troncon(String nomRue, int longueur, int vitesse, Noeud origine, Noeud destination) {
		this.setNomRue(nomRue);
		this.setLongueur(longueur);
		this.setVitesse(vitesse);
		this.setOrigine(origine);
		this.setDestination(destination);
	}

	public String getNomRue() {
		return this.nomRue;
	}

	public void setNomRue(String nomRue) {
		this.nomRue = nomRue;
	}

	public int getLongueur() {
		return this.longueur;
	}

	public void setLongueur(int longueur) {
		if (longueur > 0) {
			this.longueur = longueur;
		} else {
			this.longueur = 0;
		}
	}

	public int getVitesse() {
		return this.vitesse;
	}

	public void setVitesse(int vitesse) {
		if (vitesse > 0) {
			this.vitesse = vitesse;
		} else {
			vitesse = 0;
		}
	}

	public Noeud getOrigine() {
		return this.origine;
	}

	public void setOrigine(Noeud origine) {
		if (origine != null) {
			this.origine = origine;
		} else {
			this.origine = new Noeud(-1, 0, 0);
		}
	}

	public Noeud getDestination() {
		return this.destination;
	}

	public void setDestination(Noeud destination) {
		if (destination != null) {
			this.destination = destination;
		} else {
			this.destination = new Noeud(-1, 0, 0);
		}
	}

	@Override
	public String toString() {
		return "Troncon [nomRue=" + this.nomRue + ", longueur=" + this.longueur + ", vitesse=" + this.vitesse
				+ ", origine=" + this.origine + ", destination=" + this.destination + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.destination == null) ? 0 : this.destination.hashCode());
		result = (prime * result) + this.longueur;
		result = (prime * result) + ((this.nomRue == null) ? 0 : this.nomRue.hashCode());
		result = (prime * result) + ((this.origine == null) ? 0 : this.origine.hashCode());
		result = (prime * result) + this.vitesse;
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
		Troncon other = (Troncon) obj;
		if (this.destination == null) {
			if (other.destination != null) {
				return false;
			}
		} else if (!this.destination.equals(other.destination)) {
			return false;
		}
		if (this.longueur != other.longueur) {
			return false;
		}
		if (this.nomRue == null) {
			if (other.nomRue != null) {
				return false;
			}
		} else if (!this.nomRue.equals(other.nomRue)) {
			return false;
		}
		if (this.origine == null) {
			if (other.origine != null) {
				return false;
			}
		} else if (!this.origine.equals(other.origine)) {
			return false;
		}
		if (this.vitesse != other.vitesse) {
			return false;
		}
		return true;
	}
}
