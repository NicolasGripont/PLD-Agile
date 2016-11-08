package controleur;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modeles.Gestionnaire;
import modeles.Livraison;
import modeles.Noeud;
import vue.choixDemandeLivraisonsVue.ChoixDemandeLivraisonsVue;
import vue.choixPlanVilleVue.ChoixPlanVilleVue;
import vue.gestionLivraisonsVue.GestionLivraisonsVue;
import vue.gestionTourneeVue.GestionTourneeVue;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Controleur de l'application
 */
public class Controleur extends Application {
	
	private Gestionnaire gestionnaire = new Gestionnaire(this);
	
	/**
	 * Définition des vues
	 */
	protected ChoixDemandeLivraisonsVue choixDemandeLivraisonsVue;
	protected ChoixPlanVilleVue choixPlanVilleVue;
	protected GestionLivraisonsVue gestionLivraisonsVue;
	protected GestionTourneeVue gestionTourneeVue;
	
	/**
	 * Etat correspondant à l'état durant lequel une action est effectuée
	 */
	protected EtatDefaut etatCourant;
	
	protected ListeModifications listeModifications = new ListeModifications();
	
	/**
	 * Définition des différents états de l'application
	 */
	protected final EtatApplicationDemarree 	etatApplicationDemarree 	= new EtatApplicationDemarree();
	protected final EtatFichierLivraisonsChoisi etatFichierLivraisonsChoisi = new EtatFichierLivraisonsChoisi();
	protected final EtatLivraisonsAffichees 	etatLivraisonsAffichees 	= new EtatLivraisonsAffichees();
	protected final EtatPlanVilleAffiche 		etatPlanVilleAffiche 		= new EtatPlanVilleAffiche();
	protected final EtatPlanVilleChoisie 		etatPlanVilleChoisie 		= new EtatPlanVilleChoisie();
	protected final EtatTourneeAffiche 			etatTourneeAffiche 			= new EtatTourneeAffiche();
	protected final EtatModifierTournee 		etatModifierTournee 		= new EtatModifierTournee();
	protected final EtatAjouterTourneePlace 	etatAjouterTourneePlace 	= new EtatAjouterTourneePlace();
	protected final EtatAjouterTourneeOrdre 	etatAjouterTourneeOrdre 	= new EtatAjouterTourneeOrdre();
	protected final EtatAjouterTourneeDuree 	etatAjouterTourneeDuree 	= new EtatAjouterTourneeDuree();
	
	
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
	
	/**
	 * Permet de lancer l'application en affichant la vue du choix de plan.
	 */
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
	
	/**
	 * Permet de stopper le calcul d'une tournée.
	 */
	@Override
	public void stop() throws Exception {
		super.stop();
		gestionnaire.stopperCalculTournee();
	}
	
	/**
	 * Permet de faire un glisser déposer pour le fichier à prendre en compte.
	 * Cette méthode est utilisée pour le fichier du plan et le fichier pour les livraisons.
	 * 
	 * @param accepte : indique si le fichier est accepté ou non.
	 * @param fichier : fichier xml correspondant au fichier que l'on veut traiter
	 */
	public void glisserDeposer(boolean accepte,File fichier)
	{
		etatCourant.glisserDeposer(this,accepte,fichier);
		etatCourant.getEtat();
	}
	
	
	/**
	 * Permet de choisir le fichier à prendre en compte pour le plan ou les livraisons en cliquant sur le bouton parcourir.
	 * 
	 * @param accepte : indique si le fichier est accepté ou non.
	 * @param fichier : fichier xml correspondant au fichier que l'on veut traiter.
	 */
	public void clicBoutonParcourir(boolean accepte,File fichier)
	{
		etatCourant.clicBoutonParcourir(this,accepte,fichier);
		etatCourant.getEtat();
	}

	
	/**
	 * Permet de revenir à la vue précédente.
	 */
	public void clicBoutonRetour() {
		etatCourant.clicBoutonRetour(this, gestionnaire);
		etatCourant.getEtat();
	}
	
	/**
	 * Permet de revenir à la vue initiale.
	 */
	public void clicBoutonHome() {
		etatCourant.clicBoutonHome(this, gestionnaire);
		etatCourant.getEtat();
	}

	/**
	 * Permet d'afficher la vue suivante en affichant le plan avec ou sans livraisons correspondant au fichier choisi.
	 * 
	 * @param fichierChoisie : Fichier xml pris en compte.
	 */
	public void clicBoutonValider(File fichierChoisie) {
		etatCourant.clicBoutonValider(gestionnaire, this, fichierChoisie);
		etatCourant.getEtat();
	}

	/**
	 * Permet de lancer le calcul d'une tournée.
	 */
	public void clicBoutonCalculerTournee() {
		etatCourant.clicBoutonCalculerTournee(this, gestionnaire);
		etatCourant.getEtat();
	}
	
	/**
	 * Permet de sauvegarder la tournée calculée et affichée.
	 */
	public void clicBoutonSauvegarder() {
		etatCourant.clicBoutonSauvegarder(this);
		etatCourant.getEtat();
	}
	
	/**
	 * Permet d'annuler les modifications apportées à la tournée.
	 */
	public void clicBoutonAnnuler() {
		etatCourant.clicBoutonAnnuler(this, gestionnaire);
		etatCourant.getEtat();
	}
	
	/**
	 * Permet de supprimer une livraison de la tournée.
	 * 
	 * @param numLigne : Ligne du tableau de la livraison à supprimer.
	 */
	public void clicBoutonSupprimer(int numLigne) {
		etatCourant.clicBoutonSupprimer(this, gestionnaire, numLigne);
		etatCourant.getEtat();
	}
	
	/**
	 * Permet d'ajouter une livraison dans la tournée. Il faudra ensuite choisir sa position sur le plan.
	 */
	public void clicBoutonAjouter() {
		etatCourant.clicBoutonAjouter(this);
		etatCourant.getEtat();
	}
	
	/**
	 * Permet de modifier l'ordre d'une livraison dans le tableau.
	 * 
	 * @param numLigne : Ligne du tableau de la livraison modifiée.
	 * @param nouveauNumLigne : Nouvelle ligne du tableau de la livraison si on a changé son ordre de passage.
	 */
	public void modifierOrdre(int numLigne, int nouveauNumLigne) {
		etatCourant.modifierOrdre(this, gestionnaire, numLigne, nouveauNumLigne);
		etatCourant.getEtat();
	}
	
	/**
	 * Permet de modifier la plage de début d'une livraison dans le tableau.
	 * 
	 * @param numLigne : Ligne du tableau de la livraison modifiée.
	 * @param debutPlage : Plage horaire de début.
	 */
	public void modifierPlageDebut(int numLigne, String debutPlage) {
		etatCourant.modifierPlageDebut(this, gestionnaire, numLigne, debutPlage);
		etatCourant.getEtat();
	}
	
	/**
	 * Permet de modifier la plage de fin d'une livraison dans le tableau.
	 * 
	 * @param numLigne : Ligne du tableau de la livraison modifiée.
	 * @param finPlage : Plage horaire de fin.
	 */
	public void modifierPlageFin(int numLigne, String finPlage) {
		etatCourant.modifierPlageFin(this, gestionnaire, numLigne, finPlage);
		etatCourant.getEtat();
	}
	
	/**
	 * Permet de cliquer sur un noeud du plan.
	 * 
	 * @param noeud : Noeud sur lequel on a cliqué.
	 */
	public void clicPlanNoeud(Noeud noeud) {
		etatCourant.clicPlanNoeud(this, gestionnaire, noeud);
		etatCourant.getEtat();
	}
	
	/**
	 * Permet de cliquer sur une livraison dans le plan.
	 * 
	 * @param noeud : Livraison sur laquelle on a cliqué.
	 */
	public void clicPlanLivraison(Noeud noeud) {
		etatCourant.clicPlanLivraison(this, gestionnaire, noeud);
		etatCourant.getEtat();
	}
	
	/**
	 * Permet de modifier la durée d'une livraison.
	 * 
	 * @param duree : Durée que l'on veut modifier.
	 */
	public void entrerDuree(int duree) {
		etatCourant.entrerDuree(this, gestionnaire, duree);
		etatCourant.getEtat();
	}
	
	/**
	 * Permet de générer la feuille de route de la tournée calculée.
	 * 
	 * @param link : Chemin du fichier que l'on va créer.
	 */
	public void clicBoutonGenererFeuilleDeRoute(String link) {
		etatCourant.clicBoutonGenererFeuilleDeRoute(this, gestionnaire, link);
		etatCourant.getEtat();
	}
		
	/**
	 * Permet de stopper le calcul d'une tournée.
	 */
	public void clicBoutonsStopperCalculeTournee() {
		etatCourant.clicBoutonStopperTournee(this, gestionnaire);
		etatCourant.getEtat();
	}
	
	/**
	 * Met à jour la vue qui amène sur les modifications de la tournée.
	 */
	public void clicBoutonModifier() {
		etatCourant.clicBoutonModifier(this);
		etatCourant.getEtat();
	}
	
	/**
	 * Redessine le plan.
	 */
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