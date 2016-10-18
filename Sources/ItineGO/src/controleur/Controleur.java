package controleur;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modeles.Gestionnaire;
import modeles.ParseurLivraison;
import modeles.ParseurPlan;
import modeles.Plan;
import vue.choixDemandeLivraisons.ChoixDemandeLivraisonsVue;
import vue.choixPlanVille.ChoixPlanVilleVue;
import vue.gestionLivraisonsVue.GestionLivraisonsVue;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Controleur extends Application{
	
	private Gestionnaire gestionnaire;
	protected ChoixDemandeLivraisonsVue choixDemandeLivraisonsVue;
	protected ChoixPlanVilleVue choixPlanVilleVue;
	protected GestionLivraisonsVue gestionLivraisonsVue;
	protected EtatDefaut etatCourant = new EtatApplicationDemarree();
	protected final EtatApplicationDemarree etatApplicationDemarree = new EtatApplicationDemarree();
	protected final EtatFichierLivraisonsChoisi etatFichierLivraisonsChoisi = new EtatFichierLivraisonsChoisi();
	protected final EtatLivraisonsAffichees etatLivraisonsAffichees = new EtatLivraisonsAffichees();
	protected final EtatPlanVilleAffiche etatPlanVilleAffiche = new EtatPlanVilleAffiche();
	protected final EtatPlanVilleChoisie etatPlanVilleChoisie = new EtatPlanVilleChoisie();
	protected final EtatTourneeAffiche etatTourneeAffiche = new EtatTourneeAffiche();
	protected Stage stage;
	private Plan plan = null;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	protected void setEtatCourant(EtatDefaut etat)
	{
		etatCourant = etat;
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
				choixDemandeLivraisonsVue.dessinePlan();
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
				gestionLivraisonsVue.dessinePlan();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean creerPlanVille(File fichierPlanVilleXML) {
		plan = new Plan();
		ParseurPlan.parseurPlanVille(fichierPlanVilleXML.getAbsolutePath(), plan);
		return plan != null;
	}
	
	public boolean creerDemandeLivraison(File fichierDemandeLivraisonXML) {
		ParseurLivraison.parseurLivraisonVille(fichierDemandeLivraisonXML.getAbsolutePath(), plan);
		return plan != null;
	}
	
	public void glisserDeposer(boolean accepte,File fichier)
	{
		etatCourant.glisserDeposer(this,accepte,fichier);
	}
	
	public void clicBoutonParcourir(boolean accepte,File fichier)
	{
		etatCourant.clicBoutonParcourir(this,accepte,fichier);
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
