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
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 */
	public void clicBoutonHome(Controleur controleur, Gestionnaire gestionnaire)
	{
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
	 * Permet de retourner à la vue précédente où on affiche les livraisons a effectuées et à l'état EtatLivraisonsAffichees.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 */
	public void clicBoutonRetour(Controleur controleur, Gestionnaire gestionnaire)
	{
		gestionnaire.effacerTournee();
		if(controleur.stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/gestionLivraisonsVue/GestionLivraisonsVue.fxml"));
				Parent root = fxmlLoader.load();
				controleur.gestionLivraisonsVue = (GestionLivraisonsVue) fxmlLoader.getController();
				controleur.gestionLivraisonsVue.setControleur(controleur);
				Scene scene = new Scene(root, controleur.stage.getScene().getWidth(), controleur.stage.getScene().getHeight());
				controleur.stage.setTitle("Itine'GO");
				controleur.stage.setScene(scene);
				controleur.stage.show();
				System.out.println(controleur.stage.getHeight());
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
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 * @param link : Chemin du fichier que l'on va créer.
	 */
	public void clicBoutonGenererFeuilleDeRoute(Controleur controleur, Gestionnaire gestionnaire, String link)
	{
		gestionnaire.genererFeuilleDeRoute(link);
	}
	
	/**
	 * Met à jour la vue pour modifier la tournée.
	 */
	public void clicBoutonModifier(Controleur controleur) {
		controleur.gestionTourneeVue.majEtatModifierTournee();
		controleur.listeModifications.creerModification();
		controleur.setEtatCourant(controleur.etatModifierTournee);
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
	
	public void undo(Controleur controleur) {
		try {
			controleur.listeModifications.undoModifications();
		} catch (NonRespectPlagesHoraires e) {
			e.printStackTrace();
		}
	}
	
	public void redo(Controleur controleur) {
		try {
			controleur.listeModifications.redoModifications();
		} catch (NonRespectPlagesHoraires e) {
			e.printStackTrace();
		}
	}
	
	public void getEtat()
	{
		System.out.println("etat tournee affiche");
	}
}
