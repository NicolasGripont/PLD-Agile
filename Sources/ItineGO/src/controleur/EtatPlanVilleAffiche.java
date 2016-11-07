package controleur;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import modeles.Gestionnaire;
import vue.choixPlanVilleVue.ChoixPlanVilleVue;

/**
 * Etat d'affichage du plan de la ville, une fois que le fichier a été choisi
 */
public class EtatPlanVilleAffiche extends EtatDefaut {

	/**
	 * Permet de faire un glisser deposer d'un fichier, si le fichier est accepté on passe a l'état EtatFichierLivraisonsChoisi sinon on reste en l'état. 
	 * On va indiquer à la vue si le fichier est accepté ou non et celle-ci affichera un message correspondant.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param accepte : Indique si le fichier a été accepté ou non.
	 * @param fichier : Fichier pris en compte.
	 */
	public void glisserDeposer(Controleur controleur, boolean accepte, File fichier)
	{
		if(accepte) {
			controleur.choixDemandeLivraisonsVue.fichierAccepte(fichier);
			//changement d'état
			controleur.setEtatCourant(controleur.etatFichierLivraisonsChoisi);
		} else {
			controleur.choixDemandeLivraisonsVue.fichierRefuse();
		}
	}
	
	/**
	 * Permet choisir un fichier, si le fichier est accepté on passe a l'état EtatFichierLivraisonsChoisi sinon on reste en l'état. 
	 * On va indiquer à la vue si le fichier est accepté ou non et celle-ci affichera un message correspondant.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param accepte : Indique si le fichier a été accepté ou non.
	 * @param fichier : Fichier pris en compte.
	 */
	public void clicBoutonParcourir(Controleur controleur, boolean accepte, File fichier)
	{
		if(accepte) {
			controleur.choixDemandeLivraisonsVue.fichierAccepte(fichier);
			//changement d'état
			controleur.setEtatCourant(controleur.etatFichierLivraisonsChoisi);
		} else {
			controleur.choixDemandeLivraisonsVue.fichierRefuse();
		}
	}
	
	/**
	 * Permet de retourner à la vue précédente ChoixPlanVilleVue et à l'état EtatApplicationDemarree.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 */
	public void clicBoutonRetour(Controleur controleur, Gestionnaire gestionnaire)
	{
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void getEtat()
	{
		System.out.println("etat plan ville affiche");
	}
	
	/**
	 * Permet de redessiner le plan dans la vue.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param gestionnaire : Gestionnaire de l'application.
	 */
	public void redessinerPlan(Controleur controleur, Gestionnaire gestionnaire)
	{
		controleur.choixDemandeLivraisonsVue.dessinePlan(gestionnaire.getPlan());
	}
}
