package controleur;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modeles.Gestionnaire;
import vue.choixDemandeLivraisonsVue.ChoixDemandeLivraisonsVue;
import vue.choixPlanVilleVue.ChoixPlanVilleVue;
import vue.gestionLivraisonsVue.GestionLivraisonsVue;
import vue.gestionTourneeVue.GestionTourneeVue;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Controleur extends Application{
	
	private Gestionnaire gestionnaire = new Gestionnaire(this);
	protected ChoixDemandeLivraisonsVue choixDemandeLivraisonsVue;
	protected ChoixPlanVilleVue choixPlanVilleVue;
	protected GestionLivraisonsVue gestionLivraisonsVue;
	protected GestionTourneeVue gestionTourneeVue;
	protected EtatDefaut etatCourant;
	protected final EtatApplicationDemarree etatApplicationDemarree = new EtatApplicationDemarree();
	protected final EtatFichierLivraisonsChoisi etatFichierLivraisonsChoisi = new EtatFichierLivraisonsChoisi();
	protected final EtatLivraisonsAffichees etatLivraisonsAffichees = new EtatLivraisonsAffichees();
	protected final EtatPlanVilleAffiche etatPlanVilleAffiche = new EtatPlanVilleAffiche();
	protected final EtatPlanVilleChoisie etatPlanVilleChoisie = new EtatPlanVilleChoisie();
	protected final EtatTourneeAffiche etatTourneeAffiche = new EtatTourneeAffiche();
	protected Stage stage;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	protected void setEtatCourant(EtatDefaut etat)
	{
		etatCourant = etat;
	}
	
	public EtatDefaut getEtatCourant()
	{
		return etatCourant;
	}
	
	@Override
	public void start(Stage primaryStage) {
		setEtatCourant(etatApplicationDemarree);
		stage = primaryStage;
		stage.setMinWidth(800); //	Largeur minimum fixée
		stage.setMinHeight(580); // Hauteur minimum fixée
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/choixPlanVilleVue/ChoixPlanVille.fxml"));
			Parent root;
			root = fxmlLoader.load();
			choixPlanVilleVue = (ChoixPlanVilleVue) fxmlLoader.getController();
			choixPlanVilleVue.setControleur(this);
			Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
			stage.setTitle("Itine'GO");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void glisserDeposer(boolean accepte,File fichier)
	{
		etatCourant.glisserDeposer(this,accepte,fichier);
		etatCourant.getEtat();
	}
	
	public void clicBoutonParcourir(boolean accepte,File fichier)
	{
		etatCourant.clicBoutonParcourir(this,accepte,fichier);
		etatCourant.getEtat();
	}

	public void clicBoutonRetour() {
		etatCourant.clicBoutonRetour(this, gestionnaire);
		etatCourant.getEtat();
	}
	
	public void clicBoutonHome() {
		etatCourant.clicBoutonHome(this, gestionnaire);
		etatCourant.getEtat();
	}

	public void clicBoutonValider(File fichierChoisie) {
		etatCourant.clicBoutonValider(gestionnaire, this, fichierChoisie);
		etatCourant.getEtat();
	}

	public void clicBoutonCalculerTournee() {
		etatCourant.clicBoutonCalculerTournee(this, gestionnaire);
		etatCourant.getEtat();
	}
	
	public void clicBoutonGenererFeuilleDeRoute(String link) {
		etatCourant.clicBoutonGenererFeuilleDeRoute(this, gestionnaire, link);
		etatCourant.getEtat();
	}
		
	public void redessinerPlan() {
		etatCourant.redessinerPlan(this, gestionnaire);
	}

	public Stage getStage() {
		return stage;
	}
	
	public Gestionnaire getGestionnaire() {
		return gestionnaire;
	}
}