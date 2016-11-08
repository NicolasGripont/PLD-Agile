package controleur;

import modeles.Gestionnaire;
import modeles.Livraison;
import modeles.Noeud;

public class EtatAjouterTourneePlace extends EtatDefaut {

	/**
	 * On répond à l'action d'un clique sur un Noeud.
	 * 
	 * @param controleur
	 * @param gestionnaire
	 * @param noeud
	 */
	public void clicPlanNoeud(Controleur controleur, Gestionnaire gestionnaire, Noeud noeud) {
		if(gestionnaire.isNoeudLivraison(noeud) || gestionnaire.isNoeudEntrepot(noeud)) {
			controleur.gestionTourneeVue.afficherErreur("Ce noeud ne peut être sélectionné");
		} else {
			Livraison livraison = new Livraison(noeud);
			gestionnaire.setLivraisonEnCourCreation(livraison);
			controleur.gestionTourneeVue.majAjouterTourneeOrdre(livraison);
			controleur.setEtatCourant(controleur.etatAjouterTourneeOrdre);
		}
	}
	
	public void clicBoutonAnnuler(Controleur controleur, Gestionnaire gestionnaire) {
		controleur.listeModifications.annulerModification();
		controleur.gestionTourneeVue.majVisualiserTournee();
		controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(), gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(), 
				gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
		controleur.setEtatCourant(controleur.etatTourneeAffiche);
	}
	
	public void getEtat()
	{
		System.out.println("etat ajouter place");
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
