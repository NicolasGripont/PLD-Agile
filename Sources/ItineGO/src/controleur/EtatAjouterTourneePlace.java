package controleur;

import modeles.Gestionnaire;
import modeles.Noeud;

public class EtatAjouterTourneePlace extends EtatDefaut {

	/**
	 * On répond à l'action d'un clique sur un Noeud.
	 * 
	 * @param controleur
	 * @param gestionnaire
	 * @param noeud
	 * @param numLigne
	 */
	public void clicPlanNoeud(Controleur controleur, Gestionnaire gestionnaire, Noeud noeud, int numLigne) {
	}
	
	public void getEtat()
	{
		System.out.println("etat ajouter place");
	}
}
