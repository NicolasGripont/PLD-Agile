package controleur;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modeles.Gestionnaire;
import modeles.Noeud;
import vue.choixDemandeLivraisonsVue.ChoixDemandeLivraisonsVue;
import vue.choixPlanVilleVue.ChoixPlanVilleVue;
import vue.gestionLivraisonsVue.GestionLivraisonsVue;
import vue.gestionTourneeVue.GestionTourneeVue;

/**
 * Controleur de l'application
 */
public class Controleur extends Application {

	private final Gestionnaire gestionnaire = new Gestionnaire(this);

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
	protected final EtatApplicationDemarree etatApplicationDemarree = new EtatApplicationDemarree();
	protected final EtatFichierLivraisonsChoisi etatFichierLivraisonsChoisi = new EtatFichierLivraisonsChoisi();
	protected final EtatLivraisonsAffichees etatLivraisonsAffichees = new EtatLivraisonsAffichees();
	protected final EtatPlanVilleAffiche etatPlanVilleAffiche = new EtatPlanVilleAffiche();
	protected final EtatPlanVilleChoisie etatPlanVilleChoisie = new EtatPlanVilleChoisie();
	protected final EtatTourneeAffiche etatTourneeAffiche = new EtatTourneeAffiche();
	protected final EtatModifierTournee etatModifierTournee = new EtatModifierTournee();
	protected final EtatAjouterTourneePlace etatAjouterTourneePlace = new EtatAjouterTourneePlace();
	protected final EtatAjouterTourneeOrdre etatAjouterTourneeOrdre = new EtatAjouterTourneeOrdre();
	protected final EtatAjouterTourneeDuree etatAjouterTourneeDuree = new EtatAjouterTourneeDuree();

	protected Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	protected void setEtatCourant(EtatDefaut etat) {
		this.etatCourant = etat;
	}

	public EtatDefaut getEtatCourant() {
		return this.etatCourant;
	}

	/**
	 * Permet de lancer l'application en affichant la vue du choix de plan.
	 */
	@Override
	public void start(Stage primaryStage) {
		this.setEtatCourant(this.etatApplicationDemarree);
		this.stage = primaryStage;
		this.stage.setMinWidth(900); // Largeur minimum fixée
		this.stage.setMinHeight(620); // Hauteur minimum fixée
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					this.getClass().getResource("/vue/choixPlanVilleVue/ChoixPlanVille.fxml"));
			Parent root;
			root = fxmlLoader.load();
			this.choixPlanVilleVue = (ChoixPlanVilleVue) fxmlLoader.getController();
			this.choixPlanVilleVue.setControleur(this);
			Scene scene = new Scene(root, this.stage.getWidth(), this.stage.getHeight());
			this.stage.setTitle("Itine'GO");
			this.stage.setScene(scene);
			this.stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de faire un glisser déposer pour le fichier à prendre en compte.
	 * Cette méthode est utilisée pour le fichier du plan et le fichier pour les
	 * livraisons.
	 * 
	 * @param accepte
	 *            : indique si le fichier est accepté ou non.
	 * @param fichier
	 *            : fichier xml correspondant au fichier que l'on veut traiter
	 */
	public void glisserDeposer(boolean accepte, File fichier) {
		this.etatCourant.glisserDeposer(this, accepte, fichier);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet de choisir le fichier à prendre en compte pour le plan ou les
	 * livraisons en cliquant sur le bouton parcourir.
	 * 
	 * @param accepte
	 *            : indique si le fichier est accepté ou non.
	 * @param fichier
	 *            : fichier xml correspondant au fichier que l'on veut traiter.
	 */
	public void clicBoutonParcourir(boolean accepte, File fichier) {
		this.etatCourant.clicBoutonParcourir(this, accepte, fichier);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet de revenir à la vue précédente.
	 */
	public void clicBoutonRetour() {
		this.etatCourant.clicBoutonRetour(this, this.gestionnaire);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet de revenir à la vue initiale.
	 */
	public void clicBoutonHome() {
		this.etatCourant.clicBoutonHome(this, this.gestionnaire);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet d'afficher la vue suivante en affichant le plan avec ou sans
	 * livraisons correspondant au fichier choisi.
	 * 
	 * @param fichierChoisie
	 *            : Fichier xml pris en compte.
	 */
	public void clicBoutonValider(File fichierChoisie) {
		this.etatCourant.clicBoutonValider(this.gestionnaire, this, fichierChoisie);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet de lancer le calcul d'une tournée.
	 */
	public void clicBoutonCalculerTournee() {
		this.etatCourant.clicBoutonCalculerTournee(this, this.gestionnaire);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet de sauvegarder la tournée calculée et affichée.
	 */
	public void clicBoutonSauvegarder() {
		this.etatCourant.clicBoutonSauvegarder(this);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet d'annuler les modifications apportées à la tournée.
	 */
	public void clicBoutonAnnuler() {
		this.etatCourant.clicBoutonAnnuler(this, this.gestionnaire);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet de supprimer une livraison de la tournée.
	 * 
	 * @param numLigne
	 *            : Ligne du tableau de la livraison à supprimer.
	 */
	public void clicBoutonSupprimer(int numLigne) {
		this.etatCourant.clicBoutonSupprimer(this, this.gestionnaire, numLigne);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet d'ajouter une livraison dans la tournée. Il faudra ensuite choisir
	 * sa position sur le plan.
	 */
	public void clicBoutonAjouter() {
		this.etatCourant.clicBoutonAjouter(this);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet de modifier l'ordre d'une livraison dans le tableau.
	 * 
	 * @param numLigne
	 *            : Ligne du tableau de la livraison modifiée.
	 * @param nouveauNumLigne
	 *            : Nouvelle ligne du tableau de la livraison si on a changé son
	 *            ordre de passage.
	 */
	public void modifierOrdre(int numLigne, int nouveauNumLigne) {
		this.etatCourant.modifierOrdre(this, this.gestionnaire, numLigne, nouveauNumLigne);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet de modifier la plage de début d'une livraison dans le tableau.
	 * 
	 * @param numLigne
	 *            : Ligne du tableau de la livraison modifiée.
	 * @param debutPlage
	 *            : Plage horaire de début.
	 */
	public void modifierPlageDebut(int numLigne, String debutPlage) {
		this.etatCourant.modifierPlageDebut(this, this.gestionnaire, numLigne, debutPlage);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet de modifier la plage de fin d'une livraison dans le tableau.
	 * 
	 * @param numLigne
	 *            : Ligne du tableau de la livraison modifiée.
	 * @param finPlage
	 *            : Plage horaire de fin.
	 */
	public void modifierPlageFin(int numLigne, String finPlage) {
		this.etatCourant.modifierPlageFin(this, this.gestionnaire, numLigne, finPlage);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet de cliquer sur un noeud du plan.
	 * 
	 * @param noeud
	 *            : Noeud sur lequel on a cliqué.
	 */
	public void clicPlanNoeud(Noeud noeud) {
		this.etatCourant.clicPlanNoeud(this, this.gestionnaire, noeud);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet de cliquer sur une livraison dans le plan.
	 * 
	 * @param noeud
	 *            : Livraison sur laquelle on a cliqué.
	 */
	public void clicPlanLivraison(Noeud noeud, int numLigne) {
		this.etatCourant.clicPlanLivraison(this, this.gestionnaire, noeud, numLigne);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet de modifier la durée d'une livraison.
	 * 
	 * @param duree
	 *            : Durée que l'on veut modifier.
	 */
	public void entrerDuree(int duree) {
		this.etatCourant.entrerDuree(this, this.gestionnaire, duree);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet de générer la feuille de route de la tournée calculée.
	 * 
	 * @param link
	 *            : Chemin du fichier que l'on va créer.
	 */
	public void clicBoutonGenererFeuilleDeRoute(String link) {
		this.etatCourant.clicBoutonGenererFeuilleDeRoute(this, this.gestionnaire, link);
		this.etatCourant.getEtat();
	}

	/**
	 * Permet de stopper le calcul d'une tournée.
	 */
	public void clicBoutonsStopperCalculeTournee() {
		this.etatCourant.clicBoutonStopperTournee(this, this.gestionnaire);
		this.etatCourant.getEtat();
	}

	/**
	 * Met à jour la vue qui amène sur les modifications de la tournée.
	 */
	public void clicBoutonModifier() {
		this.etatCourant.clicBoutonModifier(this);
		this.etatCourant.getEtat();
	}

	public void undo() {
		this.etatCourant.undo(this, this.gestionnaire);
		this.etatCourant.getEtat();
	}

	public void redo() {
		this.etatCourant.redo(this, this.gestionnaire);
		this.etatCourant.getEtat();
	}

	/**
	 * Redessine le plan.
	 */
	public void redessinerPlan() {
		this.etatCourant.redessinerPlan(this, this.gestionnaire);
	}

	public Stage getStage() {
		return this.stage;
	}

	public Gestionnaire getGestionnaire() {
		return this.gestionnaire;
	}

}