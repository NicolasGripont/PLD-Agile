package modeles;

/**
 * Classe permettant de mieux gérer les horaires à traiter dans l'application
 */
public class Horaire {
	private int heure = 0;
	private int minute = 0;
	private int seconde = 0;

	/**
	 * Constructeur par recopie de la classe
	 */
	public Horaire(Horaire horaire) {
		this.setHeure(horaire.heure);
		this.setMinute(horaire.minute);
		this.setSeconde(horaire.seconde);
	}

	/**
	 * Constructeur de la classe
	 */
	public Horaire(int heure, int minute, int seconde) {
		this.setHeure(heure);
		this.setMinute(minute);
		this.setSeconde(seconde);
	}

	/**
	 * Constructeur de la classe
	 */
	public Horaire(int seconde) {
		this.ajouterSeconde(seconde);
	}

	/**
	 * Constructeur de la classe La string horaire est analysée pour remplir les
	 * attributs de la classe
	 * 
	 * @param horaire
	 *            Doit être sous la forme : heures:minutes:secondes
	 */
	public Horaire(String horaire) {
		String[] hor = horaire.split(":");
		if (hor.length == 3) {
			try {
				int h = Integer.parseInt(hor[0]);
				int m = Integer.parseInt(hor[1]);
				int s = Integer.parseInt(hor[2]);
				this.setHeure(h);
				this.setMinute(m);
				this.setSeconde(s);
			} catch (Exception e) {
				this.setHeure(0);
				this.setMinute(0);
				this.setSeconde(0);
			}
		} else {
			this.setHeure(0);
			this.setMinute(0);
			this.setSeconde(0);
		}
	}

	/**
	 * Renvoie l'horaire sous forme d'une durée en minutes depuis 00:00:00
	 * 
	 * @return Equivalent de l'horaire en minutes depuis 00:00:00
	 */
	public int getHoraireEnMinutes() {
		return Math.round((this.heure * 60) + this.minute + (this.seconde / 60));
	}

	/**
	 * Renvoie l'horaire sous forme d'une durée en secondes depuis 00:00:00
	 * 
	 * @return Equivalent de l'horaire en secondes depuis 00:00:00
	 */
	public int getHoraireEnSecondes() {
		return Math.round((this.heure * 3600) + (this.minute * 60) + this.seconde);
	}

	/**
	 * Ajoute le nombre d'heures spécifiées à l'horaire
	 */
	public void ajouterHeure(int heure) {
		int h = this.heure + heure;
		this.heure = Math.abs(h % 24);
	}

	/**
	 * Ajoute le nombre de minutes spécifiées à l'horaire
	 */
	public void ajouterMinute(int minute) {
		int m = this.minute + minute;
		this.minute = Math.abs(m % 60);
		this.ajouterHeure(m / 60);
	}

	/**
	 * Ajoute le nombre de secondes spécifiées à l'horaire
	 */
	public void ajouterSeconde(int seconde) {
		int s = this.seconde + seconde;
		this.seconde = Math.abs(s % 60);
		this.ajouterMinute(s / 60);
	}

	/**
	 * Renvoie l'horaire sous forme de string
	 */
	public String getHoraire() {
		String horaire = "";
		if (this.heure < 10) {
			horaire += "0";
		}
		horaire += this.heure;
		horaire += ":";
		if (this.minute < 10) {
			horaire += "0";
		}
		horaire += this.minute;
		return horaire;
	}

	@Override
	public String toString() {
		return this.heure + ":" + this.minute + ":" + this.seconde;
	}

	public int getHeure() {
		return this.heure;
	}

	public void setHeure(int heure) {
		if ((heure >= 0) && (heure <= 23)) {
			this.heure = heure;
		} else {
			this.heure = 0;
		}
	}

	public int getMinute() {
		return this.minute;
	}

	public void setMinute(int minute) {
		if ((minute >= 0) && (minute <= 59)) {
			this.minute = minute;
		} else {
			this.minute = 0;
		}
	}

	public int getSeconde() {
		return this.seconde;
	}

	public void setSeconde(int seconde) {
		if ((seconde >= 0) && (seconde <= 59)) {
			this.seconde = seconde;
		} else {
			this.seconde = 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + this.heure;
		result = (prime * result) + this.minute;
		result = (prime * result) + this.seconde;
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
		Horaire other = (Horaire) obj;
		if (this.heure != other.heure) {
			return false;
		}
		if (this.minute != other.minute) {
			return false;
		}
		if (this.seconde != other.seconde) {
			return false;
		}
		return true;
	}
}
