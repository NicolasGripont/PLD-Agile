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
	 */
	@Override
	public void clicPlanLivraison(Controleur controleur, Gestionnaire gestionnaire, Noeud noeudLivraison,
			int numLigne) {
		if (gestionnaire.isNoeudLivraison(noeudLivraison) || gestionnaire.isNoeudEntrepot(noeudLivraison)) {
			gestionnaire.setNoeudSuivant(noeudLivraison);
			gestionnaire.setPositionLivraisonEnCours(numLigne);
			controleur.gestionTourneeVue.majAjouterTourneeDuree();
			controleur.setEtatCourant(controleur.etatAjouterTourneeDuree);
		} else {
			controleur.gestionTourneeVue.afficherErreur("Veuillez sélectionner une livraison");
		}
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
		System.out.println("etat ajouter ordre");
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
