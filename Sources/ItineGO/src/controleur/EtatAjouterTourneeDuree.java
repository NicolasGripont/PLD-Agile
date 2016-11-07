package controleur;

import modeles.Gestionnaire;

public class EtatAjouterTourneeDuree extends EtatDefaut {

	/**
	 * 
	 */
	public void entrerDuree	(Controleur controleur, Gestionnaire gestionnaire, int duree) {
		gestionnaire.getLivraisonEnCourCreation().setDuree(duree);
		gestionnaire.ajouterLivraisonTournee();
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
		controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(), gestionnaire.listeLivraisonsParOrdreDePassage(), 
				gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
		controleur.setEtatCourant(controleur.etatModifierTournee);
	}
}
