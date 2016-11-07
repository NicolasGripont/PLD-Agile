package controleur;

import java.io.File;
import java.io.IOException;

import exceptions.BadXmlFile;
import exceptions.BadXmlPlan;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import modeles.Gestionnaire;
import vue.choixDemandeLivraisonsVue.ChoixDemandeLivraisonsVue;

/**
 * Etat après choix du fichier plan de la ville
 */
public class EtatPlanVilleChoisie extends EtatDefaut {

	/**
	 * Permet de valider le fichier pris en compte et de passer à la vue suivante où le plan est affiché.
	 * Si le fichier n'est pas bon on reste en l'état, sinon on passe a l'état EtatPlanVilleAffiche.
	 * 
	 * @param gestionnaire : Gestionnaire de l'application.
	 * @param controleur : Controleur de l'application.
	 * @param fichierXML : Fichier xml pris en compte.
	 */
	public void clicBoutonValider(Gestionnaire gestionnaire, Controleur controleur, File fichierXML)
	{
		//modification du modèle
		try {
			gestionnaire.chargerPlan(fichierXML);
			//modification des vues
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
					//changement d'état
					controleur.setEtatCourant(controleur.etatPlanVilleAffiche);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch(BadXmlFile exception) {
			controleur.choixPlanVilleVue.afficherErreur(exception.getMessage());
			controleur.setEtatCourant(controleur.etatApplicationDemarree);
		} catch(BadXmlPlan exception) {
			controleur.choixPlanVilleVue.afficherErreur(exception.getMessage());
			controleur.setEtatCourant(controleur.etatApplicationDemarree);
		}
	}
	
	/**
	 * Permet de faire un glisser deposer d'un fichier, si le fichier est accepté on reste en l'état sinon on revient à l'état EtatApplicationDemarree. 
	 * On va indiquer à la vue si le fichier est accepté ou non et celle-ci affichera un message correspondant.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param accepte : Indique si le fichier a été accepté ou non.
	 * @param fichier : Fichier pris en compte.
	 */
	public void glisserDeposer(Controleur controleur, boolean accepte, File fichier)
	{
		if(accepte) {
			controleur.choixPlanVilleVue.fichierAccepte(fichier);
		} else {
			controleur.choixPlanVilleVue.fichierRefuse();
			//changement d'état
			controleur.setEtatCourant(controleur.etatApplicationDemarree);
		}
	}
	
	/**
	 * Permet de choisir un fichier, si le fichier est accepté on reste en l'état sinon on revient à l'état EtatApplicationDemarree. 
	 * On va indiquer à la vue si le fichier est accepté ou non et celle-ci affichera un message correspondant.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param accepte : Indique si le fichier a été accepté ou non.
	 * @param fichier : Fichier pris en compte.
	 */
	public void clicBoutonParcourir(Controleur controleur, boolean accepte, File fichier)
	{
		if(accepte) {
			controleur.choixPlanVilleVue.fichierAccepte(fichier);
		} else {
			controleur.choixPlanVilleVue.fichierRefuse();
			//changement d'état
			controleur.setEtatCourant(controleur.etatApplicationDemarree);
		}
	}
	
	public void redessinerPlan(Controleur controleur, Gestionnaire gestionnaire)
	{

	}
	
	public void getEtat()
	{
		System.out.println("etat PlanVilleChoisie");
	}
}
