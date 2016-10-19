package controleur;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import modeles.Gestionnaire;
import vue.choixPlanVille.ChoixPlanVilleVue;
import vue.gestionLivraisonsVue.GestionLivraisonsVue;

public class EtatFichierLivraisonsChoisi extends EtatDefaut {

	public void clicBoutonValider(Gestionnaire gestionnaire, Controleur controleur, File fichierXML)
	{
		//TODO : Voir pour les erreurs liées au parseur
		gestionnaire.chargerLivraisons(fichierXML);
		if(controleur.stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/GestionLivraisonsVue/GestionLivraisonsVue.fxml"));
				Parent root = fxmlLoader.load();
				controleur.gestionLivraisonsVue = (GestionLivraisonsVue) fxmlLoader.getController();
				controleur.gestionLivraisonsVue.setControleur(controleur);
				controleur.gestionLivraisonsVue.setPlan(gestionnaire.getPlan());
				Scene scene = new Scene(root);
				controleur.stage.setTitle("Itine'GO");
				controleur.stage.setScene(scene);
				controleur.stage.show();
				controleur.gestionLivraisonsVue.dessinePlan();
				controleur.setEtatCourant(controleur.etatLivraisonsAffichees);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void clicBoutonRetour(Controleur controleur, Gestionnaire gestionnaire)
	{
		gestionnaire.effacerNoeudsEtTroncons();
		if(controleur.stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/choixPlanVille/ChoixPlanVille.fxml"));
				Parent root;
				root = fxmlLoader.load();
				controleur.choixPlanVilleVue = (ChoixPlanVilleVue) fxmlLoader.getController();
				controleur.choixPlanVilleVue.setControleur(controleur);
				Scene scene = new Scene(root);
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
	
}
