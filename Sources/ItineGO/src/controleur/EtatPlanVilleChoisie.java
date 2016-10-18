package controleur;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import vue.choixDemandeLivraisons.ChoixDemandeLivraisonsVue;
import modeles.Gestionnaire;

public class EtatPlanVilleChoisie extends EtatDefaut {

	public void clicBoutonValider(Controleur controleur, Gestionnaire gestionnaire, File fichierXML)
	{
		//modification du modèle
		gestionnaire.chargerPlan(fichierXML);
		//modification des vues
		if(controleur.stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/ChoixDemandeLivraisons/ChoixDemandeLivraisons.fxml"));
				Parent root = fxmlLoader.load();
				controleur.choixDemandeLivraisonsVue = (ChoixDemandeLivraisonsVue) fxmlLoader.getController();
				controleur.choixDemandeLivraisonsVue.setControleur(controleur);
				controleur.choixDemandeLivraisonsVue.setPlan(gestionnaire.getPlan());
				Scene scene = new Scene(root);
				controleur.stage.setTitle("Itine'GO");
				controleur.stage.setScene(scene);
				controleur.stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//changement d'état
		controleur.setEtatCourant(controleur.etatPlanVilleAffiche);
	}
}
