package modeles;

/**
 * Classe modélisant une livraison de la tournée
 */
public class Livraison {
	/**
	 * La durée de la livraison
	 */
	private int duree;
	/**
	 * Noeud représentant la position de la livraison
	 */
	private Noeud noeud;
	/**
	 * Indique le début de la plage horaire où l'on peut effectuer la livraison
	 * Si pas de plage, est mis à null
	 */
	private Horaire debutPlage;
	/**
	 * Indique la fin de la plage horaire où l'on peut effectuer la livraison
	 * Si pas de plage, est mis à null
	 */
	private Horaire finPlage;
	/**
	 * Heure effective d'arrivée à la livraison
	 */
	private Horaire heureArrive;
	/**
	 * Heure effective de départ de la livraison
	 */
	private Horaire heureDepart;

	/**
	 * Constructeur de la classe
	 */
	public Livraison(Noeud noeud, int duree, int heureDebutPlage, int minuteDebutPlage, 
			int secondeDebutPlage, int heureFinPlage, int minuteFinPlage, int secondeFinPlage) {
		setNoeud(noeud);
		setDuree(duree);
		setDebutPlage(new Horaire(heureDebutPlage, minuteDebutPlage, secondeDebutPlage));
		setFinPlage(new Horaire(heureFinPlage, minuteFinPlage, secondeFinPlage));
	}
	
	/**
	 * Constructeur de la classe
	 */
	public Livraison(Noeud noeud, int duree, Horaire debutPlage, Horaire finPlage) {
		setNoeud(noeud);
		setDuree(duree);
		setDebutPlage(debutPlage);
		setFinPlage(finPlage);
	}
	
	/**
	 * Constructeur de la classe
	 */
	public Livraison(Noeud noeud, int duree, String debutPlage, String finPlage) {
		setNoeud(noeud);
		setDuree(duree);
		setDebutPlage(new Horaire(debutPlage));
		setFinPlage(new Horaire(finPlage));
	}
	
	/**
	 * Constructeur de la classe
	 */
	public Livraison(Noeud noeud, int duree) {
		setNoeud(noeud);
		setDuree(duree);
	}
	
	/**
	 * Constructeur avec uniquement le noeud
	 * @param noeud
	 */
	public Livraison(Noeud noeud) {
		setNoeud(noeud);
	}

	public boolean sontValidesPlages() {
		return(heureArrive.getSeconde() >= debutPlage.getSeconde() && heureArrive.getSeconde() < finPlage.getSeconde());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((debutPlage == null) ? 0 : debutPlage.hashCode());
		result = prime * result + duree;
		result = prime * result + ((finPlage == null) ? 0 : finPlage.hashCode());
		result = prime * result + ((noeud == null) ? 0 : noeud.hashCode());
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
		Livraison other = (Livraison) obj;
		if (debutPlage == null) {
			if (other.debutPlage != null)
				return false;
		} else if (!debutPlage.equals(other.debutPlage))
			return false;
		if (duree != other.duree)
			return false;
		if (finPlage == null) {
			if (other.finPlage != null)
				return false;
		} else if (!finPlage.equals(other.finPlage))
			return false;
		if (noeud == null) {
			if (other.noeud != null)
				return false;
		} else if (!noeud.equals(other.noeud))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Livraison [noeud=" + noeud + ", duree=" + duree + ", debutPlage=" + debutPlage + ", finPlage="
				+ finPlage + "]";
	}

	public Noeud getNoeud() {
		return noeud;
	}

	public void setNoeud(Noeud noeud) {
		if(noeud == null) {
			this.noeud = new Noeud(-1,0,0);
		} else {
			this.noeud = noeud;
		}
	}

	public Horaire getDebutPlage() {
		return debutPlage;
	}

	public void setDebutPlage(Horaire debutPlage) {
		if(debutPlage != null) {
			this.debutPlage = debutPlage;
		} else {
			this.debutPlage = new Horaire(0,0,0);
		}
	}

	public Horaire getFinPlage() {
		return finPlage;
	}

	public void setFinPlage(Horaire finPlage) {
		if(finPlage != null) {
			this.finPlage = finPlage;
		} else {
			this.finPlage = new Horaire(0,0,0);
		}
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		if(duree > 0) {
			this.duree = duree;
		} else {
			this.duree = 0;
		}
	} 
	
	public Horaire getHeureArrive() {
		return heureArrive;
	}

	public void setHeureArrive(Horaire heureArrive) {
		if(heureArrive != null)
			this.heureArrive = heureArrive;
	}

	public Horaire getHeureDepart() {
		return heureDepart;
	}

	public void setHeureDepart(Horaire heureDepart) {
		if(heureDepart != null)
			this.heureDepart = heureDepart;
	}
}
