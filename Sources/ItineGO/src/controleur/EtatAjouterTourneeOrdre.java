package controleur;

import modeles.Gestionnaire;
import modeles.Livraison;
import modeles.Noeud;

public class EtatAjouterTourneeOrdre extends EtatDefaut {

	/**
	 * On répond à l'action d'un clique sur une Livraison.
	 * 
	 * @param controleur
	 * @param gestionnaire
	 * @param noeud
	 */
	public void clicPlanLivraison(Controleur controleur, Gestionnaire gestionnaire, Noeud noeudLivraison) {
		if(gestionnaire.isNoeudLivraison(noeudLivraison)) {
			Livraison livraisonSuivante = gestionnaire.getPlan().getLivraisons().get(noeudLivraison);
			gestionnaire.setLivraisonSuivante(livraisonSuivante);
			controleur.gestionTourneeVue.majAjouterTourneeDuree();
			controleur.setEtatCourant(controleur.etatAjouterTourneeDuree);
		} else {
			controleur.gestionTourneeVue.afficherErreur("Veuillez sélectionner une livraison");
		}
	}
	
	public void clicBoutonAnnuler(Controleur controleur, Gestionnaire gestionnaire) {
		controleur.listeModifications.annulerModification();
		controleur.gestionTourneeVue.setLabelInstructionVisible(false);
		controleur.gestionTourneeVue.setVisibiliteBoutons(false);
		controleur.gestionTourneeVue.setSupprimerColonneVisible(false);
		controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(), gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(), 
				gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
		controleur.setEtatCourant(controleur.etatTourneeAffiche);
	}
	
	public void getEtat()
	{
		System.out.println("etat ajouter ordre");
	}
}
