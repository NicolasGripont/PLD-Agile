package controleur;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import modeles.Gestionnaire;
import vue.choixDemandeLivraisonsVue.ChoixDemandeLivraisonsVue;
import vue.choixPlanVilleVue.ChoixPlanVilleVue;
import vue.gestionTourneeVue.GestionTourneeVue;

/**
 * Etat d'affichage de l'entrepot et des livraisons à effectuer avant calcul de
 * la tournée
 */
public class EtatLivraisonsAffichees extends EtatDefaut {
	private Thread threadCalcul;
	private Thread threadConstructionTournee;

	/**
	 * Permet de calculer la tournée a effectué.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 */
	@Override
	public void clicBoutonCalculerTournee(Controleur controleur, Gestionnaire gestionnaire) {
		this.threadCalcul = new Thread() {
			@Override
			public void run() {
				gestionnaire.calculerTournee();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						EtatLivraisonsAffichees.this.afficherTournee(controleur, gestionnaire,
								gestionnaire.estSolutionOptimale());
					}
				});
				EtatLivraisonsAffichees.this.stopperCalculTournee();
			}
		};
		this.threadConstructionTournee = new Thread() {
			@Override
			public void run() {
				while (EtatLivraisonsAffichees.this.threadCalcul.isInterrupted() == false) {
					try {
						Thread.sleep(3000);
						gestionnaire.getPlan().constructionTourneePendantCalculDeTournee();
					} catch (InterruptedException e) {
						return;
					}
				}
				gestionnaire.getPlan().constructionTourneePendantCalculDeTournee();
				if (gestionnaire != null) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							gestionnaire.tourneeCalculee();
						}
					});
				}
			}
		};
		this.threadConstructionTournee.setDaemon(true);
		this.threadConstructionTournee.start();
		this.threadCalcul.setDaemon(true);
		this.threadCalcul.start();
	}

	/**
	 * Permet de stopper le calcul de la tournée. Si une solution a été trouvée
	 * on l'affiche, sinon on affiche un message d'erreur.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 */
	@Override
	public void clicBoutonStopperTournee(Controleur controleur, Gestionnaire gestionnaire) {
		this.stopperCalculTournee();
		if (gestionnaire.solutionTrouvee()) {
			this.afficherTournee(controleur, gestionnaire, false);
		} else {
			controleur.gestionLivraisonsVue.afficherErreur("Aucune solution trouvée");
		}
	}

	/**
	 * Permet d'afficher la tournée que l'on a calculé. On indique si la
	 * solution trouvée est optimale ou non.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 * @param solutionOptimale
	 *            : Indique si la solution est optimale ou non.
	 */
	@Override
	public void afficherTournee(Controleur controleur, Gestionnaire gestionnaire, boolean solutionOptimale) {
		if (controleur.stage != null) {
			if (gestionnaire.solutionTrouvee()) {
				try {
					this.stopperCalculTournee();
					FXMLLoader fxmlLoader = new FXMLLoader(
							this.getClass().getResource("/vue/gestionTourneeVue/GestionTourneeVue.fxml"));
					Parent root = fxmlLoader.load();
					controleur.gestionTourneeVue = (GestionTourneeVue) fxmlLoader.getController();
					controleur.gestionTourneeVue.setControleur(controleur);
					Scene scene = new Scene(root, controleur.stage.getScene().getWidth(),
							controleur.stage.getScene().getHeight());
					controleur.stage.setTitle("Itine'GO");
					controleur.stage.setScene(scene);
					controleur.stage.show();
					controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
					controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(),
							gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(),
							gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
					controleur.gestionTourneeVue.solutionOptimale(solutionOptimale);
					controleur.gestionTourneeVue.majVisualiserTournee();
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
					controleur.setEtatCourant(controleur.etatTourneeAffiche);
				} catch (IOException e) {

					e.printStackTrace();
				}
			} else {
				controleur.gestionLivraisonsVue.afficherErreur("Aucune solution trouvée");
				controleur.gestionLivraisonsVue.setHboxCalculLivraison(false);
			}
		}
	}

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
		this.stopperCalculTournee();
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
				controleur.listeModifications.viderListeModifications();
				controleur.setEtatCourant(controleur.etatApplicationDemarree);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * Permet de retourner à la vue précédente ChoixDemandeLivraisonsVue et à
	 * l'état EtatPlanVilleAffiche.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 */
	@Override
	public void clicBoutonRetour(Controleur controleur, Gestionnaire gestionnaire) {
		this.stopperCalculTournee();
		gestionnaire.effacerTournee();
		gestionnaire.effacerLivraisonsEtEntrepot();
		if (controleur.stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						this.getClass().getResource("/vue/ChoixDemandeLivraisonsVue/ChoixDemandeLivraisons.fxml"));
				Parent root = fxmlLoader.load();
				controleur.choixDemandeLivraisonsVue = (ChoixDemandeLivraisonsVue) fxmlLoader.getController();
				controleur.choixDemandeLivraisonsVue.setControleur(controleur);
				Scene scene = new Scene(root, controleur.stage.getScene().getWidth(),
						controleur.stage.getScene().getHeight());
				controleur.stage.setTitle("Itine'GO");
				controleur.stage.setScene(scene);
				controleur.stage.show();
				controleur.choixDemandeLivraisonsVue.dessinePlan(gestionnaire.getPlan());
				controleur.listeModifications.viderListeModifications();
				controleur.setEtatCourant(controleur.etatPlanVilleAffiche);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Permet de redessiner le plan dans la vue.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 */
	@Override
	public void redessinerPlan(Controleur controleur, Gestionnaire gestionnaire) {
		controleur.gestionLivraisonsVue.dessinePlan(gestionnaire.getPlan());
	}

	@Override
	public void getEtat() {
		System.out.println("etat livraisons affichées");
	}

	/**
	 * Permet de stopper le thread de calcul tournee
	 */
	private void stopperCalculTournee() {
		if ((this.threadCalcul != null) && this.threadCalcul.isAlive()
				&& (this.threadCalcul.isInterrupted() == false)) {
			this.threadCalcul.interrupt();
		}
		if ((this.threadConstructionTournee != null) && this.threadConstructionTournee.isAlive()
				&& (this.threadConstructionTournee.isInterrupted() == false)) {
			this.threadConstructionTournee.interrupt();
		}
	}
}
