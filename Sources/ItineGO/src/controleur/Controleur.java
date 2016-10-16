package controleur;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modeles.ParserLivraison;
import modeles.ParserPlan;
import modeles.Plan;
import vue.choixDemandeLivraisons.ChoixDemandeLivraisonsVue;
import vue.choixPlanVille.ChoixPlanVilleVue;
import vue.gestionLivraisonsVue.GestionLivraisonsVue;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Controleur extends Application{
	private ChoixDemandeLivraisonsVue choixDemandeLivraisonsVue;
	private ChoixPlanVilleVue choixPlanVilleVue;
	private GestionLivraisonsVue gestionLivraisonsVue;
	private Stage stage;
	private Plan plan = null;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		showChoixPlanVille();
	}
	
	public void showChoixPlanVille() {
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
	
	public void showChoixDemandeLivraison() {
		if(stage != null) {
			try {
				plan.resetLivraisons();
				plan.setEntrepot(null);
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/ChoixDemandeLivraisons/ChoixDemandeLivraisons.fxml"));
				Parent root = fxmlLoader.load();
				choixDemandeLivraisonsVue = (ChoixDemandeLivraisonsVue) fxmlLoader.getController();
				choixDemandeLivraisonsVue.setControleur(this);
				choixDemandeLivraisonsVue.setPlan(plan);
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
	
	public void showGestionLivraisons() {
		if(stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/GestionLivraisonsVue/GestionLivraisonsVue.fxml"));
				Parent root = fxmlLoader.load();
				gestionLivraisonsVue = (GestionLivraisonsVue) fxmlLoader.getController();
				gestionLivraisonsVue.setControleur(this);
				gestionLivraisonsVue.setPlan(plan);
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
	
	public boolean creerPlanVille(File fichierPlanVilleXML) {
		plan = new Plan();
		ParserPlan.parserPlanVille(fichierPlanVilleXML.getAbsolutePath(), plan);
		return plan != null;
	}
	
	public boolean creerDemandeLivraison(File fichierDemandeLivraisonXML) {
		ParserLivraison.parserLivraisonVille(fichierDemandeLivraisonXML.getAbsolutePath(), plan);
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
