package controleur;

import modeles.Gestionnaire;
import modeles.Horaire;
import modeles.Livraison;
import modeles.Noeud;

public class EtatAjouterTourneePlace extends EtatDefaut {

	/**
	 * Permet de définir le noeud de la livraison qui va suivre la livraison que l'on veut créer.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 * @param noeud
	 *            : Noeud de la livraison que l'on veut créer.
	 */
	@Override
	public void clicPlanNoeud(Controleur controleur, Gestionnaire gestionnaire, Noeud noeud) {
		if (gestionnaire.isNoeudLivraison(noeud) || gestionnaire.isNoeudEntrepot(noeud)) {
			controleur.gestionTourneeVue.afficherErreur("Ce noeud ne peut être sélectionné");
		} else {
			Livraison livraison = new Livraison(noeud);
			livraison.setDebutPlage(new Horaire("0:0:0"));
			livraison.setFinPlage(new Horaire("0:0:0"));
			gestionnaire.setLivraisonEnCourCreation(livraison);
			controleur.gestionTourneeVue.majAjouterTourneeOrdre(livraison);
			controleur.setEtatCourant(controleur.etatAjouterTourneeOrdre);
		}
	}

	/**
	 * Permet d'annuler l'ajout.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 */
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
		System.out.println("etat ajouter place");
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
