package controleur;

import modeles.Gestionnaire;

/**
 *	Etat lorsque l'on modifie la tournée. 
 */
public class EtatModifierTournee extends EtatDefaut {

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
		controleur.gestionTourneeVue.setLabelInstructionVisible(false);
		controleur.gestionTourneeVue.setVisibiliteBoutons(false);
		controleur.gestionTourneeVue.setSupprimerColonneVisible(false);
		controleur.setEtatCourant(controleur.etatTourneeAffiche);
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
	
	/**
	 * Permet de modifier l'ordre d'une livraison.
	 * 
	 * @param numLigne : Ligne du tableau de la livraison modifiée.
	 * @param nouveauNumLigne : Nouvelle ligne du tableau de la livraison si on a changé son ordre de passage. 
	 */
	public void modifierOrdre(Controleur controleur, Gestionnaire gestionnaire, int numLigne, int nouveauNumLigne) {		
	}
	
	/**
	 * Permet de modifier la plage de début d'une livraison dans le tableau.
	 * 
	 * @param numLigne : Ligne du tableau de la livraison modifiée.
	 * @param debutPlage : Plage horaire de début.
	 */
	public void modifierPlageDebut(Controleur controleur, Gestionnaire gestionnaire, int numLigne, String plageDebut) {
	}
	
	/**
	 * Permet de modifier la plage de début d'une livraison dans le tableau.
	 * 
	 * @param numLigne : Ligne du tableau de la livraison modifiée.
	 * @param finPlage : Plage horaire de fin.
	 */
	public void modifierPlageFin(Controleur controleur, Gestionnaire gestionnaire, int numLigne, String plageFin) {
	}
	
}
