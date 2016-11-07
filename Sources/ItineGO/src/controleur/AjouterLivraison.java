package controleur;

import modeles.Gestionnaire;
import modeles.Livraison;

public class AjouterLivraison extends Commande {

	private Livraison livraison;
	private Gestionnaire gestionnaire;
	
	public AjouterLivraison(Gestionnaire gestionnaire, Livraison livraison) {
		this.livraison = livraison;
		this.gestionnaire = gestionnaire;
	}
	
	
	@Override
	public void doCode() {

	}

	@Override
	public void undoCode() {

	}

}
