package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;

public class EtatAjouterTourneeDuree extends EtatDefaut {

	/**
	 * 
	 */
	@Override
	public void entrerDuree(Controleur controleur, Gestionnaire gestionnaire, int duree) {
		gestionnaire.getLivraisonEnCoursCreation().setDuree(duree);
		AjouterLivraison ajouterLivraison = new AjouterLivraison(gestionnaire,
				gestionnaire.getPositionLivraisonEnCours());
		try {
			controleur.listeModifications.ajouterCommande(ajouterLivraison);
			controleur.gestionTourneeVue.afficherInfo(
					"La nouvelle livraison a été ajoutée, on ne peut garantir que la solution est optimale");
		} catch (NonRespectPlagesHoraires e) {
			controleur.gestionTourneeVue.afficherErreur(
					"La nouvelle livraison a été ajoutée, l'ajout de la livraison ne permet pas de respecter les plages horaires");
		}
		controleur.gestionTourneeVue.majEtatModifierTournee();
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
		controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(),
				gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(),
				gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
		controleur.setEtatCourant(controleur.etatModifierTournee);
	}

	@Override
	public void clicBoutonAnnuler(Controleur controleur, Gestionnaire gestionnaire) {
		controleur.gestionTourneeVue.majEtatModifierTournee();
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
		controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(),
				gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(),
				gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
		controleur.gestionTourneeVue.afficherInfo("L'ajout de la nouvelle livraison a été annulé");
		controleur.setEtatCourant(controleur.etatModifierTournee);
	}

	@Override
	public void getEtat() {
		System.out.println("etat ajouter duree");
	}

	/**
	 * Permet de redessiner le plan dans la vue correspondante.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 */
	@Override
	public void redessinerPlan(Controleur controleur, Gestionnaire gestionnaire) {
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
	}
}
