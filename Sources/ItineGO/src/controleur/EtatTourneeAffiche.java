package controleur;

import java.io.IOException;

import exceptions.NonRespectPlagesHoraires;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import modeles.Gestionnaire;
import vue.choixPlanVilleVue.ChoixPlanVilleVue;
import vue.gestionLivraisonsVue.GestionLivraisonsVue;

/**
 * Etat d'affichage de la tournée, une fois le calcul effectué
 */
public class EtatTourneeAffiche extends EtatDefaut {

	/**
	 * Permet de retourner à l'état initial de l'application.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 */
	@Override
	public void clicBoutonHome(Controleur controleur, Gestionnaire gestionnaire) {
		controleur.listeModifications.undoAllModifications();
		controleur.listeModifications.viderListeModifications();
		gestionnaire.effacerTournee();
		gestionnaire.effacerLivraisonsEtEntrepot();
		gestionnaire.effacerNoeudsEtTroncons();
		if (controleur.stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						this.getClass().getResource("/vue/choixPlanVilleVue/ChoixPlanVille.fxml"));
				Parent root;
				root = fxmlLoader.load();
				controleur.choixPlanVilleVue = (ChoixPlanVilleVue) fxmlLoader.getController();
				controleur.choixPlanVilleVue.setControleur(controleur);
				Scene scene = new Scene(root, controleur.stage.getScene().getWidth(),
						controleur.stage.getScene().getHeight());
				controleur.stage.setTitle("Itine'GO");
				controleur.stage.setScene(scene);
				controleur.stage.show();
				controleur.setEtatCourant(controleur.etatApplicationDemarree);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Permet de retourner à la vue précédente où on affiche les livraisons a
	 * effectuées et à l'état EtatLivraisonsAffichees.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 */
	@Override
	public void clicBoutonRetour(Controleur controleur, Gestionnaire gestionnaire) {
		controleur.listeModifications.undoAllModifications();
		controleur.listeModifications.viderListeModifications();
		gestionnaire.effacerTournee();
		if (controleur.stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						this.getClass().getResource("/vue/gestionLivraisonsVue/GestionLivraisonsVue.fxml"));
				Parent root = fxmlLoader.load();
				controleur.gestionLivraisonsVue = (GestionLivraisonsVue) fxmlLoader.getController();
				controleur.gestionLivraisonsVue.setControleur(controleur);
				Scene scene = new Scene(root, controleur.stage.getScene().getWidth(),
						controleur.stage.getScene().getHeight());
				controleur.stage.setTitle("Itine'GO");
				controleur.stage.setScene(scene);
				controleur.stage.show();
				controleur.gestionLivraisonsVue.dessinePlan(gestionnaire.getPlan());
				controleur.gestionLivraisonsVue.miseAJourTableau(gestionnaire.getPlan());
				controleur.setEtatCourant(controleur.etatLivraisonsAffichees);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Permet de générer une feuille de route de la tournée calculée.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 * @param link
	 *            : Chemin du fichier que l'on va créer.
	 */
	@Override
	public void clicBoutonGenererFeuilleDeRoute(Controleur controleur, Gestionnaire gestionnaire, String link) {
		gestionnaire.genererFeuilleDeRoute(link);
		controleur.gestionTourneeVue.afficherInfo("Feuille de route enregistrée");
	}

	/**
	 * Met à jour la vue pour modifier la tournée.
	 */
	@Override
	public void clicBoutonModifier(Controleur controleur) {
		controleur.gestionTourneeVue.majEtatModifierTournee();
		controleur.listeModifications.creerModification();
		controleur.gestionTourneeVue.afficherInfo("Modification de la tournéee");
		controleur.setEtatCourant(controleur.etatModifierTournee);
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

	/**
	 * Permet de faire un undo de la dernières liste de modifications qui viennent d'être faites.
	 * On va refaire les commandes une à une.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 */
	@Override
	public void undo(Controleur controleur, Gestionnaire gestionnaire) {
		try {
			controleur.listeModifications.undoModifications();
		} catch (NonRespectPlagesHoraires e) {
			controleur.gestionTourneeVue.afficherErreur("L'undo ne permet pas de respecter les plages horaires");
		}
		controleur.gestionTourneeVue.majVisualiserTournee();
		controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(),
				gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(),
				gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
		controleur.gestionTourneeVue.solutionOptimale(!controleur.listeModifications.isUndoPossible());
		if (controleur.listeModifications.isUndoPossible()) {
			controleur.gestionTourneeVue.desactiverUndo(false);
		} else {
			controleur.gestionTourneeVue.desactiverUndo(true);
		}
		if (controleur.listeModifications.isRedoPossible()) {
			controleur.gestionTourneeVue.desactiverRedo(false);
		} else {
			controleur.gestionTourneeVue.desactiverRedo(true);
		}
	}

	/**
	 * Permet de faire un redo de la dernières liste de modifications qui vient d'être undo. 
	 * On va refaire les commandes une à une.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 */
	@Override
	public void redo(Controleur controleur, Gestionnaire gestionnaire) {
		try {
			controleur.listeModifications.redoModifications();
		} catch (NonRespectPlagesHoraires e) {
			controleur.gestionTourneeVue.afficherErreur("Le redo ne permet pas de respecter les plages horaires");
		}
		controleur.gestionTourneeVue.majVisualiserTournee();
		controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(),
				gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(),
				gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
		controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
		controleur.gestionTourneeVue.solutionOptimale(!controleur.listeModifications.isUndoPossible());
		if (controleur.listeModifications.isUndoPossible()) {
			controleur.gestionTourneeVue.desactiverUndo(false);
		} else {
			controleur.gestionTourneeVue.desactiverUndo(true);
		}
		if (controleur.listeModifications.isRedoPossible()) {
			controleur.gestionTourneeVue.desactiverRedo(false);
		} else {
			controleur.gestionTourneeVue.desactiverRedo(true);
		}
	}

	@Override
	public void getEtat() {
		System.out.println("etat tournee affiche");
	}
}
