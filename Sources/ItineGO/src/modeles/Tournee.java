package modeles;

import java.util.List;

public class Tournee {

	private List<Trajet> trajets;

	public Tournee(List<Trajet> trajets) {
		super();
		this.trajets = trajets;
	}

	public List<Trajet> getTrajets() {
		return trajets;
	}

	@Override
	public String toString() {
		return "Tournee [trajets=" + trajets + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((trajets == null) ? 0 : trajets.hashCode());
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
		Tournee other = (Tournee) obj;
		if (trajets == null) {
			if (other.trajets != null)
				return false;
		} else if (!trajets.equals(other.trajets))
			return false;
		return true;
	} 
	
	
}
