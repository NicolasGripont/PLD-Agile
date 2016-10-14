package controleur;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import vue.choixDemandeLivraisons.ChoixDemandeLivraisonsVue;
import vue.choixPlanVille.ChoixPlanVilleVue;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Controleur extends Application{
	private ChoixDemandeLivraisonsVue choixDemandeLivraisonVue;
	private ChoixPlanVilleVue choixPlanVilleVue;
	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			ShowChoixPlanVille(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void ShowChoixPlanVille(Stage primaryStage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/choixPlanVille/ChoixPlanVille.fxml"));
		Parent root = fxmlLoader.load();
		choixPlanVilleVue = (ChoixPlanVilleVue) fxmlLoader.getController();
		choixPlanVilleVue.setControleur(this);
		Scene scene = new Scene(root);
		primaryStage.setTitle("Itine'GO");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void ShowChoixDemandeLivraison(Stage primaryStage) {
		
	}

	public ChoixDemandeLivraisonsVue getChoixDemandeLivraisonVue() {
		return choixDemandeLivraisonVue;
	}

	public ChoixPlanVilleVue getChoixPlanVilleVue() {
		return choixPlanVilleVue;
	}

	public Stage getStage() {
		return stage;
	}
}
