package modeles;

/**
 * Classe modélisant l'entrepot d'une tournée C'est à la fois le noeud de départ
 * et d'arrivée
 */
public class Entrepot {
	/**
	 * Noeud représentant la position de l'entrepot
	 */
	private Noeud noeud;

	/**
	 * Horaire de départ de la tournée
	 */
	private Horaire horaireDepart;

	/**
	 * Horraire de fin de tournée
	 */
	private Horaire horaireArrive;

	/**
	 * Constructeur de la classe
	 */
	public Entrepot(Noeud noeud, int heureDepart, int minuteDepart, int secondeDepart) {
		this.setNoeud(noeud);
		this.setHoraireDepart(new Horaire(heureDepart, minuteDepart, secondeDepart));
	}

	/**
	 * Constructeur de la classe
	 */
	public Entrepot(Noeud noeud, Horaire horaire) {
		this.setNoeud(noeud);
		this.setHoraireDepart(horaire);
	}

	/**
	 * Constructeur de la classe
	 */
	public Entrepot(Noeud noeud, String horaire) {
		this.setNoeud(noeud);
		this.setHoraireDepart(new Horaire(horaire));
	}

	/**
	 * Renvoie le noeud, position de l'entrepot
	 */
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

	/**
	 * Renvoie l'horaire de départ
	 */
	public Horaire getHoraireDepart() {
		return this.horaireDepart;
	}

	public void setHoraireDepart(Horaire horaireDepart) {
		if (horaireDepart != null) {
			this.horaireDepart = horaireDepart;
		} else {
			this.horaireDepart = new Horaire(0, 0, 0);
		}
	}

	@Override
	public String toString() {
		return "Entrepot [noeud=" + this.noeud + ", horaireDepart=" + this.horaireDepart + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.horaireDepart == null) ? 0 : this.horaireDepart.hashCode());
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
		Entrepot other = (Entrepot) obj;
		if (this.horaireDepart == null) {
			if (other.horaireDepart != null) {
				return false;
			}
		} else if (!this.horaireDepart.equals(other.horaireDepart)) {
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

	public Horaire getHoraireArrive() {
		return this.horaireArrive;
	}

	public void setHoraireArrive(Horaire horaireArrive) {
		this.horaireArrive = horaireArrive;
	}
}
