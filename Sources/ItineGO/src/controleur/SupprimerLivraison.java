package controleur;

import modeles.Gestionnaire;
import modeles.Livraison;

public class SupprimerLivraison extends Commande {

	private Livraison livraison;
	private Gestionnaire gestionnaire;
	
	public SupprimerLivraison(Gestionnaire gestionnaire, Livraison livraison) {
		this.livraison = livraison;
		this.gestionnaire = gestionnaire;
	}
	
	@Override
	public void doCode() {
		// TODO Auto-generated method stub

	}

	@Override
	public void undoCode() {
		// TODO Auto-generated method stub

	}

}
