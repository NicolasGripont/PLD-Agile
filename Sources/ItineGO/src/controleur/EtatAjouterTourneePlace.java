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
		if(gestionnaire.isNoeudLivraison(noeud)) {
			controleur.gestionTourneeVue;
			//appel fonction vue
		} else {
			Livraison livraison = new Livraison(noeud);
			gestionnaire.setLivraisonEnCourCreation(livraison);
			//appel fonction vue
			controleur.setEtatCourant(controleur.etatAjouterTourneeOrdre);
		}
	}
	
	@Override
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
		System.out.println("etat ajouter place");
	}
}
