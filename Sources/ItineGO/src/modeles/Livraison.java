package modeles;

public class Livraison {
	private int duree;
	private Noeud noeud;
	private Horaire debutPlage;
	private Horaire finPlage;
	
	public Livraison(Noeud noeud, int duree, int heureDebutPlage, int minuteDebutPlage, 
			int secondeDebutPlage, int heureFinPlage, int minuteFinPlage, int secondeFinPlage) {
		setNoeud(noeud);
		setDuree(duree);
		setDebutPlage(new Horaire(heureDebutPlage, minuteDebutPlage, secondeDebutPlage));
		setFinPlage(new Horaire(heureFinPlage, minuteFinPlage, secondeFinPlage));
	}
	
	public Livraison(Noeud noeud, int duree, Horaire debutPlage, Horaire finPlage) {
		this.setNoeud(noeud);
		setDuree(duree);
		setDebutPlage(debutPlage);
		setFinPlage(finPlage);
	}
	
	public Livraison(Noeud noeud, int duree, String debutPlage, String finPlage) {
		this.setNoeud(noeud);
		setDuree(duree);
		String[] horDebutPlage = debutPlage.split(":");
		if(horDebutPlage.length == 3) {
			try {
				int h = Integer.parseInt(horDebutPlage[0]); 
				int m = Integer.parseInt(horDebutPlage[1]); 
				int s = Integer.parseInt(horDebutPlage[2]); 
				this.setDebutPlage(new Horaire(h, m, s));
			} catch(Exception e) {
				this.setDebutPlage(new Horaire(0, 0, 0));
			}
		} else {
			this.setDebutPlage(new Horaire(0, 0, 0));
		}
		String[] horFinPlage = finPlage.split(":");
		if(horFinPlage.length == 3) {
			try {
				int h = Integer.parseInt(horFinPlage[0]); 
				int m = Integer.parseInt(horFinPlage[1]); 
				int s = Integer.parseInt(horFinPlage[2]); 
				this.setFinPlage(new Horaire(h, m, s));
			} catch(Exception e) {
				this.setFinPlage(new Horaire(0, 0, 0));
			}
		} else {
			this.setFinPlage(new Horaire(0, 0, 0));
		}
	}

	public Livraison(Noeud noeud, int duree) {
		this.setNoeud(noeud);
		setDuree(duree);
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
		return "Livraison [duree=" + duree + ", noeud=" + noeud + ", debutPlage=" + debutPlage + ", finPlage="
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
		this.debutPlage = debutPlage;
	}

	public Horaire getFinPlage() {
		return finPlage;
	}

	public void setFinPlage(Horaire finPlage) {
		this.finPlage = finPlage;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	} 
}
