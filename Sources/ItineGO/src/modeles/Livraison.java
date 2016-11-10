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
	 * Indique la fin de la plage horaire où l'on peut effectuer la livraison Si
	 * pas de plage, est mis à null
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
	public Livraison(Noeud noeud, int duree, int heureDebutPlage, int minuteDebutPlage, int secondeDebutPlage,
			int heureFinPlage, int minuteFinPlage, int secondeFinPlage) {
		this.setNoeud(noeud);
		this.setDuree(duree);
		this.setDebutPlage(new Horaire(heureDebutPlage, minuteDebutPlage, secondeDebutPlage));
		this.setFinPlage(new Horaire(heureFinPlage, minuteFinPlage, secondeFinPlage));
	}

	/**
	 * Constructeur de copie de la classe
	 */
	public Livraison(Livraison livraison) {
		this.setNoeud(livraison.noeud);
		this.setDuree(livraison.duree);
		this.setDebutPlage(livraison.debutPlage);
		this.setFinPlage(livraison.finPlage);
	}

	/**
	 * Constructeur de la classe
	 */
	public Livraison(Noeud noeud, int duree, Horaire debutPlage, Horaire finPlage) {
		this.setNoeud(noeud);
		this.setDuree(duree);
		this.setDebutPlage(debutPlage);
		this.setFinPlage(finPlage);
	}

	/**
	 * Constructeur de la classe
	 */
	public Livraison(Noeud noeud, int duree, String debutPlage, String finPlage) {
		this.setNoeud(noeud);
		this.setDuree(duree);
		this.setDebutPlage(new Horaire(debutPlage));
		this.setFinPlage(new Horaire(finPlage));
	}

	/**
	 * Constructeur de la classe
	 */
	public Livraison(Noeud noeud, int duree) {
		this.setNoeud(noeud);
		this.setDuree(duree);
		this.setDebutPlage(null);
		this.setFinPlage(null);
	}

	/**
	 * Constructeur avec uniquement le noeud
	 * 
	 * @param noeud
	 */
	public Livraison(Noeud noeud) {
		this.setNoeud(noeud);
		this.setDuree(0);
		this.setDebutPlage(null);
		this.setFinPlage(null);
	}

	public boolean sontValidesPlages() {
		if ((this.heureArrive.getHoraireEnSecondes() < this.finPlage.getHoraireEnSecondes())
				|| (this.finPlage.getHoraireEnSecondes() == 0)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.debutPlage == null) ? 0 : this.debutPlage.hashCode());
		result = (prime * result) + this.duree;
		result = (prime * result) + ((this.finPlage == null) ? 0 : this.finPlage.hashCode());
		result = (prime * result) + ((this.noeud == null) ? 0 : this.noeud.hashCode());
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
		Livraison other = (Livraison) obj;
		if (this.debutPlage == null) {
			if (other.debutPlage != null) {
				return false;
			}
		} else if (!this.debutPlage.equals(other.debutPlage)) {
			return false;
		}
		if (this.duree != other.duree) {
			return false;
		}
		if (this.finPlage == null) {
			if (other.finPlage != null) {
				return false;
			}
		} else if (!this.finPlage.equals(other.finPlage)) {
			return false;
		}
		if (this.noeud == null) {
			if (other.noeud != null) {
				return false;
			}
		} else if (!this.noeud.equals(other.noeud)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Livraison [noeud=" + this.noeud + ", duree=" + this.duree + ", debutPlage=" + this.debutPlage
				+ ", finPlage=" + this.finPlage + "]";
	}

	public Noeud getNoeud() {
		return this.noeud;
	}

	public void setNoeud(Noeud noeud) {
		if (noeud == null) {
			this.noeud = new Noeud(-1, 0, 0);
		} else {
			this.noeud = noeud;
		}
	}

	public Horaire getDebutPlage() {
		return this.debutPlage;
	}

	public void setDebutPlage(Horaire debutPlage) {
		if ((debutPlage != null)
				&& (((this.finPlage == null) || (this.finPlage.getHoraireEnMinutes() == 0) || ((this.finPlage != null)
						&& (debutPlage.getHoraireEnMinutes() < this.finPlage.getHoraireEnMinutes()))))) {
			this.debutPlage = debutPlage;
		} else {
			this.debutPlage = new Horaire(0, 0, 0);
		}
	}

	public Horaire getFinPlage() {
		return this.finPlage;
	}

	public void setFinPlage(Horaire finPlage) {
		if ((finPlage != null) && ((this.debutPlage != null)
				&& (finPlage.getHoraireEnMinutes() > (this.debutPlage.getHoraireEnMinutes() + (this.duree / 60))))) {
			this.finPlage = finPlage;
		} else {
			this.finPlage = new Horaire(0, 0, 0);
		}
	}

	public int getDuree() {
		return this.duree;
	}

	public void setDuree(int duree) {
		if (duree > 0) {
			this.duree = duree;
		} else {
			this.duree = 0;
		}
	}

	public Horaire getHeureArrive() {
		return this.heureArrive;
	}

	public void setHeureArrive(Horaire heureArrive) {
		if (heureArrive != null) {
			this.heureArrive = heureArrive;
		}
	}

	public Horaire getHeureDepart() {
		return this.heureDepart;
	}

	public void setHeureDepart(Horaire heureDepart) {
		if (heureDepart != null) {
			this.heureDepart = heureDepart;
		}
	}

	/**
	 * Renvoie le temps d'attente si l'arrivée à la livraison se fait avant le
	 * début de la plage horaire Si arrivée dans les temps renvoie 0
	 */
	public int getTempsAttente() {
		if ((this.debutPlage == null) || (this.heureArrive == null)) {
			return 0;
		}
		if ((this.debutPlage == new Horaire(0, 0, 0)) || (this.heureArrive == new Horaire(0, 0, 0))
				|| ((this.debutPlage.getHoraireEnSecondes() - this.heureArrive.getHoraireEnSecondes()) < 0)) {
			return 0;
		}
		return this.debutPlage.getHoraireEnSecondes() - this.heureArrive.getHoraireEnSecondes();
	}
}
