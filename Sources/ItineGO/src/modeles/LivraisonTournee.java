package modeles;

/*
 * Classe modélisant, le passage effectué à une livraison
 */
public class LivraisonTournee {
	/*
	 * Livraison effectuée
	 */
	private Livraison livraison;
	/*
	 * Heure effective d'arrivée à la livraison
	 */
	private Horaire heureArrive;
	/*
	 * Heure effective de départ de la livraison
	 */
	private Horaire heureDepart;
	
	/*
	 * Constructeur de la classe
	 */
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
