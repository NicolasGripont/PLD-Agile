package controleur;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import modeles.Gestionnaire;
import vue.choixPlanVilleVue.ChoixPlanVilleVue;
import vue.gestionLivraisonsVue.GestionLivraisonsVue;

public class EtatTourneeAffiche extends EtatDefaut {

	
	public void clicBoutonHome(Controleur controleur, Gestionnaire gestionnaire)
	{
		gestionnaire.effacerLivraisonsEtEntrepot();
		gestionnaire.effacerNoeudsEtTroncons();
		if(controleur.stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/choixPlanVilleVue/ChoixPlanVille.fxml"));
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
		if(controleur.stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/gestionLivraisonsVue/GestionLivraisonsVue.fxml"));
				Parent root = fxmlLoader.load();
				controleur.gestionLivraisonsVue = (GestionLivraisonsVue) fxmlLoader.getController();
				controleur.gestionTourneeVue.setControleur(controleur);
				controleur.gestionTourneeVue.setPlan(gestionnaire.getPlan());
				Scene scene = new Scene(root);
				controleur.stage.setTitle("Itine'GO");
				controleur.stage.setScene(scene);
				controleur.stage.show();
				controleur.gestionTourneeVue.dessinePlan();
				controleur.setEtatCourant(controleur.etatLivraisonsAffichees);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void getEtat()
	{
		System.out.println("etat tournee affiche");
	}
}
