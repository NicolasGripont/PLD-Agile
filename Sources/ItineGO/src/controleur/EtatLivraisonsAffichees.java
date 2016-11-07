package controleur;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import modeles.Gestionnaire;
import vue.choixDemandeLivraisonsVue.ChoixDemandeLivraisonsVue;
import vue.choixPlanVilleVue.ChoixPlanVilleVue;
import vue.gestionTourneeVue.GestionTourneeVue;

/**
 * Etat d'affichage de l'entrepot et des livraisons à effectuer avant calcul de la tournée
 */
public class EtatLivraisonsAffichees extends EtatDefaut {

	/**
	 * Permet de calculer la tournée a effectué.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 */
	public void clicBoutonCalculerTournee(Controleur controleur, Gestionnaire gestionnaire)
	{
		gestionnaire.calculerTournee();
	}
	
	/**
	 * Permet de stopper le calcul de la tournée.
	 * Si une solution a été trouvée on l'affiche, sinon on affiche un message d'erreur.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 */
	public void clicBoutonStopperTournee(Controleur controleur, Gestionnaire gestionnaire)
	{
		gestionnaire.stopperCalculTournee();
		if(gestionnaire.solutionTrouvee()) {
			afficherTournee(controleur, gestionnaire, false);
		} else {
			controleur.gestionLivraisonsVue.afficherErreur("Aucune solution trouvée");
		}
	}
	
	/**
	 * Permet d'afficher la tournée que l'on a calculé. On indique si la solution trouvée est optimale ou non.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 * @param solutionOptimale : Indique si la solution est optimale ou non.
	 */
	public void afficherTournee(Controleur controleur, Gestionnaire gestionnaire, boolean solutionOptimale) {
		if(controleur.stage != null) {
			try {
				gestionnaire.stopperCalculTournee();
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/gestionTourneeVue/GestionTourneeVue.fxml"));
				Parent root = fxmlLoader.load();
				controleur.gestionTourneeVue = (GestionTourneeVue) fxmlLoader.getController();
				controleur.gestionTourneeVue.setControleur(controleur);
				Scene scene = new Scene(root, controleur.stage.getScene().getWidth(), controleur.stage.getScene().getHeight());
				controleur.stage.setTitle("Itine'GO");
				controleur.stage.setScene(scene);
				controleur.stage.show();
				controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
				controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.getPlan(), gestionnaire.getPlan().getTournee().listeLivraisonsParOrdreDePassage(),
						gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
				controleur.gestionTourneeVue.solutionOptimale(solutionOptimale);
				controleur.listeModifications.creerModification();
				controleur.setEtatCourant(controleur.etatTourneeAffiche);	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * Permet de retourner à l'état initial de l'application.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 */
	public void clicBoutonHome(Controleur controleur, Gestionnaire gestionnaire)
	{
		gestionnaire.stopperCalculTournee();
		gestionnaire.effacerTournee();
		gestionnaire.effacerLivraisonsEtEntrepot();
		gestionnaire.effacerNoeudsEtTroncons();
		if(controleur.stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/choixPlanVilleVue/ChoixPlanVille.fxml"));
				Parent root;
				root = fxmlLoader.load();
				controleur.choixPlanVilleVue = (ChoixPlanVilleVue) fxmlLoader.getController();
				controleur.choixPlanVilleVue.setControleur(controleur);
				Scene scene = new Scene(root, controleur.stage.getScene().getWidth(), controleur.stage.getScene().getHeight());
				System.out.println("testedibzr");
				controleur.stage.setTitle("Itine'GO");
				controleur.stage.setScene(scene);
				controleur.stage.show();
				controleur.setEtatCourant(controleur.etatApplicationDemarree);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Permet de retourner à la vue précédente ChoixDemandeLivraisonsVue et à l'état EtatPlanVilleAffiche.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 */
	public void clicBoutonRetour(Controleur controleur, Gestionnaire gestionnaire)
	{
		gestionnaire.stopperCalculTournee();
		gestionnaire.effacerTournee();
		gestionnaire.effacerLivraisonsEtEntrepot();
		if(controleur.stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/ChoixDemandeLivraisonsVue/ChoixDemandeLivraisons.fxml"));
				Parent root = fxmlLoader.load();
				controleur.choixDemandeLivraisonsVue = (ChoixDemandeLivraisonsVue) fxmlLoader.getController();
				controleur.choixDemandeLivraisonsVue.setControleur(controleur);
				Scene scene = new Scene(root, controleur.stage.getScene().getWidth(), controleur.stage.getScene().getHeight());
				controleur.stage.setTitle("Itine'GO");
				controleur.stage.setScene(scene);
				controleur.stage.show();
				controleur.choixDemandeLivraisonsVue.dessinePlan(gestionnaire.getPlan());
				controleur.setEtatCourant(controleur.etatPlanVilleAffiche);	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Permet de redessiner le plan dans la vue.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 */
	public void redessinerPlan(Controleur controleur, Gestionnaire gestionnaire)
	{
		controleur.gestionLivraisonsVue.dessinePlan(gestionnaire.getPlan());
	}
	
	public void getEtat()
	{
		System.out.println("etat livraisons affichées");
	}
	
	
}
