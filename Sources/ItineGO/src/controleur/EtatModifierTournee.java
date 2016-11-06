package controleur;

import modeles.Gestionnaire;

/**
 *	Etat lorsque l'on modifie la tournée. 
 */
public class EtatModifierTournee extends EtatDefaut {

	//TODO : définir les états après chaque action
	/**
	 * Permet de sauvegarder les modifications apportées.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 */
	public void clicBoutonSauvegarder (Controleur controleur, Gestionnaire gestionnaire) {
	}
	
	/** 
	 * Permet d'annuler les modifications.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 */
	public void clicBoutonAnnuler (Controleur controleur, Gestionnaire gestionnaire) {
	}
	
	/**
	 * Permet de supprimer une ligne du tableau.
	 *  
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 * @param numLigne : Numéro de la ligne de la livraison a supprimé.
	 */
	public void clicBoutonSupprimer	(Controleur controleur, Gestionnaire gestionnaire, int numLigne) {
	}
	
	/** 
	 * Permet d'ajouter une livraison à la tournée.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 */
	public void clicBoutonAjouter (Controleur controleur, Gestionnaire gestionnaire) {
	}
}
