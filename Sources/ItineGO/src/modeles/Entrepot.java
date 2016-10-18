package modeles;

public class Entrepot {
	private Noeud noeud;
	private Horaire horaireDepart;
	
	public Entrepot(Noeud noeud, int heureDepart, int minuteDepart, int secondeDepart) {
		this.setNoeud(noeud);
		setHoraireDepart(new Horaire(heureDepart, minuteDepart, secondeDepart));
	}
	
	public Entrepot(Noeud noeud, Horaire horaire) {
		this.setNoeud(noeud);
		setHoraireDepart(horaire);
	}
	
	public Entrepot(Noeud noeud, String horaire) {
		this.setNoeud(noeud);
		this.setHoraireDepart(new Horaire(horaire));
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

	public Horaire getHoraireDepart() {
		return horaireDepart;
	}

	public void setHoraireDepart(Horaire horaireDepart) {
		if(horaireDepart != null) {
			this.horaireDepart = horaireDepart;
		} else {
			this.horaireDepart = new Horaire(0,0,0);
		}
	}

	@Override
	public String toString() {
		return "Entrepot [noeud=" + noeud + ", horaireDepart=" + horaireDepart + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((horaireDepart == null) ? 0 : horaireDepart.hashCode());
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
		Entrepot other = (Entrepot) obj;
		if (horaireDepart == null) {
			if (other.horaireDepart != null)
				return false;
		} else if (!horaireDepart.equals(other.horaireDepart))
			return false;
		if (noeud == null) {
			if (other.noeud != null)
				return false;
		} else if (!noeud.equals(other.noeud))
			return false;
		return true;
	}
}
