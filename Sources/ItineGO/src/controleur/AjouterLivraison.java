package controleur;

import exceptions.NonRespectPlagesHoraires;
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
	public void doCode() throws NonRespectPlagesHoraires {
		gestionnaire.ajouterLivraisonTournee();
	}

	@Override
	public void undoCode() {
		gestionnaire.supprimerLivraisonTournee(livraison);
	}

}
