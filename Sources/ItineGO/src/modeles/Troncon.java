package modeles;

public class Troncon {
	private String nomRue;
	private int longueur;
	private int vitesse;
	private Noeud origine;
	private Noeud destination;
	
	public Troncon(String nomRue, int longueur, int vitesse, Noeud origine, Noeud destination) {
		this.setNomRue(nomRue);
		this.setLongueur(longueur);
		this.setVitesse(vitesse);
		this.setOrigine(origine);
		this.setDestination(destination);
	}

	public String getNomRue() {
		return nomRue;
	}

	public void setNomRue(String nomRue) {
		this.nomRue = nomRue;
	}

	public int getLongueur() {
		return longueur;
	}

	public void setLongueur(int longueur) {
		if(longueur > 0) {
			this.longueur = longueur;
		} else {
			this.longueur = 0;
		}
	}

	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		if(vitesse > 0) {
			this.vitesse = vitesse;
		} else {
			vitesse = 0;
		}
	}

	public Noeud getOrigine() {
		return origine;
	}

	public void setOrigine(Noeud origine) {
		if(origine != null) {
			this.origine = origine;
		} else {
			this.origine = new Noeud(-1, 0, 0);
		}
	}

	public Noeud getDestination() {
		return destination;
	}

	public void setDestination(Noeud destination) {
		if(destination != null) {
			this.destination = destination;
		} else {
			this.destination = new Noeud(-1,0,0);
		}
	}

	@Override
	public String toString() {
		return "Troncon [nomRue=" + nomRue + ", longueur=" + longueur + ", vitesse=" + vitesse + ", origine=" + origine
				+ ", destination=" + destination + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + longueur;
		result = prime * result + ((nomRue == null) ? 0 : nomRue.hashCode());
		result = prime * result + ((origine == null) ? 0 : origine.hashCode());
		result = prime * result + vitesse;
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
		Troncon other = (Troncon) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (longueur != other.longueur)
			return false;
		if (nomRue == null) {
			if (other.nomRue != null)
				return false;
		} else if (!nomRue.equals(other.nomRue))
			return false;
		if (origine == null) {
			if (other.origine != null)
				return false;
		} else if (!origine.equals(other.origine))
			return false;
		if (vitesse != other.vitesse)
			return false;
		return true;
	}
}
