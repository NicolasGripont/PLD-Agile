package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;
import modeles.Horaire;
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
			livraison.setDebutPlage(new Horaire("0:0:0"));
			livraison.setFinPlage(new Horaire("0:0:0"));
			gestionnaire.setLivraisonEnCourCreation(livraison);
			controleur.gestionTourneeVue.majAjouterTourneeOrdre(livraison);
			controleur.setEtatCourant(controleur.etatAjouterTourneeOrdre);
		}
	}
	
	public void clicBoutonAnnuler(Controleur controleur, Gestionnaire gestionnaire) {
		/*try {
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
		}*/
		controleur.gestionTourneeVue.majEtatModifierTournee();
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
		controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(), gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(), 
				gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
		controleur.gestionTourneeVue.afficherInfo("L'ajout de la nouvelle livraison a été annulé");
		controleur.setEtatCourant(controleur.etatModifierTournee);
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
