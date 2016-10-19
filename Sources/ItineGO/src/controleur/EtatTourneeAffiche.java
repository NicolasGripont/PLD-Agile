package controleur;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import modeles.Gestionnaire;
import vue.choixDemandeLivraisonsVue.ChoixDemandeLivraisonsVue;
import vue.choixPlanVilleVue.ChoixPlanVilleVue;

public class EtatTourneeAffiche extends EtatDefaut {

	
	public void clicBoutonHome(Controleur controleur, Gestionnaire gestionnaire)
	{
		gestionnaire.effacerLivraisonsEtEntrepot();
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
	
	public void clicBoutonRetour(Controleur controleur, Gestionnaire gestionnaire)
	{
		gestionnaire.effacerTournee();
		gestionnaire.effacerLivraisonsEtEntrepot();
		if(controleur.stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/ChoixDemandeLivraisonsVue/ChoixDemandeLivraisons.fxml"));
				Parent root = fxmlLoader.load();
				controleur.choixDemandeLivraisonsVue = (ChoixDemandeLivraisonsVue) fxmlLoader.getController();
				controleur.choixDemandeLivraisonsVue.setControleur(controleur);
				controleur.choixDemandeLivraisonsVue.setPlan(gestionnaire.getPlan());
				Scene scene = new Scene(root);
				controleur.stage.setTitle("Itine'GO");
				controleur.stage.setScene(scene);
				controleur.stage.show();
				controleur.choixDemandeLivraisonsVue.dessinePlan();
				controleur.setEtatCourant(controleur.etatLivraisonsAffichees);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
