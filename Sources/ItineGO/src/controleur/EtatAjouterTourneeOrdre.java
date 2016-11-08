package controleur;

import modeles.Gestionnaire;
import modeles.Livraison;
import modeles.Noeud;

public class EtatAjouterTourneeOrdre extends EtatDefaut {

	/**
	 * On répond à l'action d'un clique sur une Livraison.
	 * 
	 * @param controleur
	 * @param gestionnaire
	 * @param noeud
	 */
	public void clicPlanLivraison(Controleur controleur, Gestionnaire gestionnaire, Noeud noeudLivraison) {
		if(gestionnaire.isNoeudLivraison(noeudLivraison)) {
			Livraison livraisonSuivante = gestionnaire.getPlan().getLivraisons().get(noeudLivraison);
			gestionnaire.setLivraisonSuivante(livraisonSuivante);
			
			controleur.setEtatCourant(controleur.etatAjouterTourneeDuree);
		} else {
			controleur.gestionTourneeVue.afficherErreur("Veuillez sélectionner une livraison");
		}
	}
	
	public void getEtat()
	{
		System.out.println("etat ajouter ordre");
	}
}
