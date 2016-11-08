package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;
import modeles.Noeud;

public class EtatAjouterTourneeOrdre extends EtatDefaut {

	/**
	 * On répond à l'action d'un clique sur une Livraison.
	 * 
	 * @param controleur
	 * @param gestionnaire
	 * @param noeud
	 */
	public void clicPlanLivraison(Controleur controleur, Gestionnaire gestionnaire, Noeud noeudLivraison,int numLigne) {
		if(gestionnaire.isNoeudLivraison(noeudLivraison) || gestionnaire.isNoeudEntrepot(noeudLivraison)) {
			gestionnaire.setNoeudSuivant(noeudLivraison);
			gestionnaire.setPositionLivraisonEnCours(numLigne);
			controleur.gestionTourneeVue.majAjouterTourneeDuree();
			controleur.setEtatCourant(controleur.etatAjouterTourneeDuree);
		} else {
			controleur.gestionTourneeVue.afficherErreur("Veuillez sélectionner une livraison");
		}
	}
	
	public void clicBoutonAnnuler(Controleur controleur, Gestionnaire gestionnaire) {
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
	
	public void getEtat()
	{
		System.out.println("etat ajouter ordre");
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
