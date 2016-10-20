package vue.gestionTourneeVue;

import modeles.Horaire;
import modeles.Livraison;

public class LivraisonTournee {
	private Livraison livraison;
	private Horaire heureArrive;
	private Horaire heureDepart;
	
	public LivraisonTournee(Livraison livraison, Horaire heureArrive, Horaire heureDepart) {
		setLivraison(livraison);
		setHeureArrive(heureArrive);
		setHeureDepart(heureDepart);
	}

	public Livraison getLivraison() {
		return livraison;
	}

	public void setLivraison(Livraison livraison) {
		if(livraison != null)
			this.livraison = livraison;
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
