package controleur;

import modeles.Gestionnaire;
import modeles.Noeud;

public class EtatAjouterTourneeOrdre extends EtatDefaut {

	/**
	 * On répond à l'action d'un clique sur une Livraison.
	 * 
	 * @param controleur
	 * @param gestionnaire
	 * @param noeud
	 * @param numLigne
	 */
	public void clicPlanLivraison(Controleur controleur, Gestionnaire gestionnaire, Noeud noeud, int numLigne) {
	}
	
	public void getEtat()
	{
		System.out.println("etat ajouter ordre");
	}
}
