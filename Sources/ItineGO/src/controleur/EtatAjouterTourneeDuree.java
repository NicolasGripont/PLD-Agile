package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;

public class EtatAjouterTourneeDuree extends EtatDefaut {

	/**
	 * 
	 */
	public void entrerDuree	(Controleur controleur, Gestionnaire gestionnaire, int duree) {
		gestionnaire.getLivraisonEnCoursCreation().setDuree(duree);
		AjouterLivraison ajouterLivraison = new AjouterLivraison(gestionnaire,gestionnaire.getPositionLivraisonEnCours());
		try {
			controleur.listeModifications.ajouterCommande(ajouterLivraison);
		} catch (NonRespectPlagesHoraires e) {
			controleur.gestionTourneeVue.afficherErreur("L'ajout de la livraison ne permet pas de respecter les plages horaires");
		}
		controleur.gestionTourneeVue.majEtatModifierTournee();
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
		controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(), gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(), 
				gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
		controleur.setEtatCourant(controleur.etatModifierTournee);
	}
	
	public void clicBoutonAnnuler(Controleur controleur, Gestionnaire gestionnaire) {
		try {
			controleur.listeModifications.annulerModification();
		} catch (NonRespectPlagesHoraires e) {
			//ne doit pas arriver
			System.out.println("Problème de plage lors de l'annulation");
		}
		controleur.gestionTourneeVue.majVisualiserTournee();
		controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(), gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(), 
				gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
		controleur.setEtatCourant(controleur.etatTourneeAffiche);
	}
	
	public void getEtat()
	{
		System.out.println("etat ajouter duree");
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
