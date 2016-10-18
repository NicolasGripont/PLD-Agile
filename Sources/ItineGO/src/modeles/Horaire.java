package modeles;

public class Horaire {
	private int heure = 0;
	private int minute = 0;
	private int seconde = 0;
	
	public Horaire(int heure, int minute, int seconde) {
		this.setHeure(heure);
		this.setMinute(minute);
		this.setSeconde(seconde);
	}
	
	public Horaire(String horaire) {
		String[] hor = horaire.split(":");
		if(hor.length == 3) {
			try {
				int h = Integer.parseInt(hor[0]); 
				int m = Integer.parseInt(hor[1]); 
				int s = Integer.parseInt(hor[2]); 
				this.setHeure(h);
				this.setMinute(m);
				this.setSeconde(s);
			} catch(Exception e) {
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

	public void ajouterHeure(int heure) {
		int h = this.heure + heure;
		this.heure = h % 24;
	}
	
	public void ajouterMinute(int minute) {
		int m = this.minute + minute;
		this.minute = m % 60;
		ajouterHeure(m/60);
	}
	
	public void ajouterSeconde(int seconde) {
		int s = this.seconde + seconde;
		this.seconde = s % 60;
		ajouterMinute(s/60);
	}
	
	@Override
	public String toString() {
		return  heure + ":" + minute + ":" + seconde;
	}

	public int getHeure() {
		return heure;
	}

	public void setHeure(int heure) {
		if(heure >= 0 && heure <= 23) {
			this.heure = heure;
		}
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		if(minute >= 0 && minute <= 59) {
			this.minute = minute;
		}
	}

	public int getSeconde() {
		return seconde;
	}

	public void setSeconde(int seconde) {
		if(seconde >= 0 && seconde <= 59) {
			this.seconde = seconde;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + heure;
		result = prime * result + minute;
		result = prime * result + seconde;
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
		Horaire other = (Horaire) obj;
		if (heure != other.heure)
			return false;
		if (minute != other.minute)
			return false;
		if (seconde != other.seconde)
			return false;
		return true;
	}
}
