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
		String[] hor = horaire.split(":");
		if(hor.length == 3) {
			try {
				int h = Integer.parseInt(hor[0]); 
				int m = Integer.parseInt(hor[1]); 
				int s = Integer.parseInt(hor[2]); 
				this.setHoraireDepart(new Horaire(h, m, s));
			} catch(Exception e) {
				this.setHoraireDepart(new Horaire(0, 0, 0));
			}
		} else {
			this.setHoraireDepart(new Horaire(0, 0, 0));
		}
	}

	public Noeud getNoeud() {
		return noeud;
	}

	public void setNoeud(Noeud noeud) {
		this.noeud = noeud;
	}

	public Horaire getHoraireDepart() {
		return horaireDepart;
	}

	public void setHoraireDepart(Horaire horaireDepart) {
		this.horaireDepart = horaireDepart;
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
