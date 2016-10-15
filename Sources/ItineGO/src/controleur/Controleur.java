package controleur;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modeles.Noeud;
import modeles.Plan;
import modeles.Troncon;
import vue.choixDemandeLivraisons.ChoixDemandeLivraisonsVue;
import vue.choixPlanVille.ChoixPlanVilleVue;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Controleur extends Application{
	private ChoixDemandeLivraisonsVue choixDemandeLivraisonsVue;
	private ChoixPlanVilleVue choixPlanVilleVue;
	private Stage stage;
	private Plan plan = null;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		ShowChoixPlanVille();
	}
	
	public void ShowChoixPlanVille() {
		if(stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/choixPlanVille/ChoixPlanVille.fxml"));
				Parent root;
				root = fxmlLoader.load();
				choixPlanVilleVue = (ChoixPlanVilleVue) fxmlLoader.getController();
				choixPlanVilleVue.setControleur(this);
				Scene scene = new Scene(root);
				stage.setTitle("Itine'GO");
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void ShowChoixDemandeLivraison() {
		if(stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/ChoixDemandeLivraisons/ChoixDemandeLivraisons.fxml"));
				Parent root = fxmlLoader.load();
				choixDemandeLivraisonsVue = (ChoixDemandeLivraisonsVue) fxmlLoader.getController();
				choixDemandeLivraisonsVue.setControleur(this);
				choixDemandeLivraisonsVue.dessinePlanVille(plan);
				Scene scene = new Scene(root);
				stage.setTitle("Itine'GO");
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean CreerPlanVille(File fichierPlanVilleXML) {
		/*
		 * Appel au parseur, si erreur le parseur doit renvoyer null
		 * Pour l'instant création aléatoire d'un plan avec 3 noeuds et 3 troncons
		 */
		plan = new Plan();
		Noeud n1 = new Noeud(1,20,20);
		Noeud n2 = new Noeud(2,100,100);
		Noeud n3 = new Noeud(3,100,20);
		plan.AjouterNoeud(n1);
		plan.AjouterNoeud(n2);
		plan.AjouterNoeud(n3);
		Troncon t1 = new Troncon("rue 1-2", 10, 10, n1, n2);
		Troncon t2 = new Troncon("rue 1-3", 10, 10, n1, n3);
		Troncon t3 = new Troncon("rue 2-3", 10, 10, n2, n3);
		plan.AjouterTroncon(t1);
		plan.AjouterTroncon(t2);
		plan.AjouterTroncon(t3);
		return plan != null;
	}

	public Plan getPlan() {
		return plan;
	}
	
	public ChoixDemandeLivraisonsVue getChoixDemandeLivraisonVue() {
		return choixDemandeLivraisonsVue;
	}

	public ChoixPlanVilleVue getChoixPlanVilleVue() {
		return choixPlanVilleVue;
	}

	public Stage getStage() {
		return stage;
	}
}
