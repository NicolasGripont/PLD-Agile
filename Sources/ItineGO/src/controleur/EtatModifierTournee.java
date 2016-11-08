package controleur;

import exceptions.NonRespectPlagesHoraires;
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
	public void clicBoutonSauvegarder (Controleur controleur) {
		controleur.listeModifications.finModification();
		controleur.gestionTourneeVue.majVisualiserTournee();
		if(controleur.listeModifications.isUndoPossible()) {
			controleur.gestionTourneeVue.desactiverUndo(false);
		}
		else {
			controleur.gestionTourneeVue.desactiverUndo(true);
		}
		if(controleur.listeModifications.isRedoPossible()) {
			controleur.gestionTourneeVue.desactiverRedo(false);
		}
		else {
			controleur.gestionTourneeVue.desactiverRedo(true);
		}
		controleur.setEtatCourant(controleur.etatTourneeAffiche);
	}
	
	/** 
	 * Permet d'annuler les modifications.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 */
	public void clicBoutonAnnuler (Controleur controleur, Gestionnaire gestionnaire) {
		try {
			controleur.listeModifications.annulerModification();
		} catch (NonRespectPlagesHoraires e) {
			e.printStackTrace();
		}
		controleur.gestionTourneeVue.majVisualiserTournee();
		controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(), gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(), 
				gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
		if(controleur.listeModifications.isUndoPossible()) {
			controleur.gestionTourneeVue.desactiverUndo(false);
		}
		else {
			controleur.gestionTourneeVue.desactiverUndo(true);
		}
		if(controleur.listeModifications.isRedoPossible()) {
			controleur.gestionTourneeVue.desactiverRedo(false);
		}
		else {
			controleur.gestionTourneeVue.desactiverRedo(true);
		}
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
		SupprimerLivraison commandeSuppression = new SupprimerLivraison(gestionnaire, numLigne);
		try {
			controleur.listeModifications.ajouterCommande(commandeSuppression);
		} catch (NonRespectPlagesHoraires e) {
			controleur.gestionTourneeVue.afficherErreur("La suppression ne permet pas de respecter les plages horaires");
		}
		controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(), gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(), 
				gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
	}
	
	/** 
	 * Permet d'ajouter une livraison à la tournée.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 */
	public void clicBoutonAjouter (Controleur controleur) {
		controleur.gestionTourneeVue.majAjouterTourneePlace();
		controleur.setEtatCourant(controleur.etatAjouterTourneePlace);
	}
	
	/**
	 * Permet de modifier l'ordre d'une livraison.
	 * 
	 * @param numLigne : Ligne du tableau de la livraison modifiée.
	 * @param nouveauNumLigne : Nouvelle ligne du tableau de la livraison si on a changé son ordre de passage. 
	 */
	public void modifierOrdre(Controleur controleur, Gestionnaire gestionnaire, int numLigne, int nouveauNumLigne) {
		ModifierOrdre commandeModifier = new ModifierOrdre(gestionnaire, numLigne, nouveauNumLigne);
		try {
			controleur.listeModifications.ajouterCommande(commandeModifier);
		} catch (NonRespectPlagesHoraires e) {
			controleur.gestionTourneeVue.afficherErreur("La modification ne permet pas de respecter les plages horaires");
		}
		controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(), gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(), 
				gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
		
	}
	
	/**
	 * Permet de modifier la plage de début d'une livraison dans le tableau.
	 * 
	 * @param numLigne : Ligne du tableau de la livraison modifiée.
	 * @param debutPlage : Plage horaire de début.
	 */
	public void modifierPlageDebut(Controleur controleur, Gestionnaire gestionnaire, int numLigne, String plageDebut) {
		ModifierPlageHoraireDebut modifierPlageHoraireDebut = new ModifierPlageHoraireDebut(gestionnaire, numLigne, plageDebut);
		try {
			controleur.listeModifications.ajouterCommande(modifierPlageHoraireDebut);
		} catch (NonRespectPlagesHoraires e) {
			controleur.gestionTourneeVue.afficherErreur("La modification ne permet pas de respecter les plages horaires");
		}
		controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(), gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(), 
				gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
	}
	
	/**
	 * Permet de modifier la plage de début d'une livraison dans le tableau.
	 * 
	 * @param numLigne : Ligne du tableau de la livraison modifiée.
	 * @param finPlage : Plage horaire de fin.
	 */
	public void modifierPlageFin(Controleur controleur, Gestionnaire gestionnaire, int numLigne, String plageFin) {
		ModifierPlageHoraireFin modifierPlageHoraireFin = new ModifierPlageHoraireFin(gestionnaire, numLigne, plageFin);
		try {
			controleur.listeModifications.ajouterCommande(modifierPlageHoraireFin);
		} catch (NonRespectPlagesHoraires e) {
			controleur.gestionTourneeVue.afficherErreur("La modification ne permet pas de respecter les plages horaires");
		}
		controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(), gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(), 
				gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
	}
	
	public void getEtat()
	{
		System.out.println("etat modifier tournee");
	}
	
	/**
	 * Permet de redessiner le plan dans la vue correspondante.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 */
	public void redessinerPlan(Controleur controleur, Gestionnaire gestionnaire)
	{
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
	}
}
