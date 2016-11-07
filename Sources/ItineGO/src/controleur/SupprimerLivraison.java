package controleur;

import modeles.Gestionnaire;
import modeles.Livraison;

public class SupprimerLivraison extends Commande {

	private Livraison livraison;
	private Gestionnaire gestionnaire;
	private Livraison livraisonSuivante;
	private int numLigne;
	
	public SupprimerLivraison(Gestionnaire gestionnaire, int numLigne) {
		this.numLigne = numLigne;
		this.gestionnaire = gestionnaire;
	}
	
	@Override
	public void doCode() {
		livraison = gestionnaire.getLivraisonTournee(numLigne);
		livraisonSuivante = gestionnaire.getLivraisonTournee(numLigne+1);
		gestionnaire.supprimerLivraisonTournee(livraison);
	}

	@Override
	public void undoCode() {
		gestionnaire.setLivraisonEnCourCreation(livraison);
		gestionnaire.setLivraisonSuivante(livraisonSuivante);
		gestionnaire.ajouterLivraisonTournee();
	}

}
