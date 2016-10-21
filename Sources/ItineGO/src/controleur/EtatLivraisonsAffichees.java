package controleur;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import modeles.Gestionnaire;
import vue.choixDemandeLivraisonsVue.ChoixDemandeLivraisonsVue;
import vue.choixPlanVilleVue.ChoixPlanVilleVue;
import vue.gestionTourneeVue.GestionTourneeVue;

public class EtatLivraisonsAffichees extends EtatDefaut {

	public void clicBoutonCalculerTournee(Controleur controleur, Gestionnaire gestionnaire)
	{
		if(gestionnaire.calculerTournee()) {
			if(controleur.stage != null) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/gestionTourneeVue/GestionTourneeVue.fxml"));
					Parent root = fxmlLoader.load();
					controleur.gestionTourneeVue = (GestionTourneeVue) fxmlLoader.getController();
					controleur.gestionTourneeVue.setControleur(controleur);
					Scene scene = new Scene(root);
					controleur.stage.setTitle("Itine'GO");
					controleur.stage.setScene(scene);
					controleur.stage.show();
					controleur.gestionTourneeVue.dessinePlan(gestionnaire.getPlan());
					controleur.gestionTourneeVue.miseAJourTableau(gestionnaire.listeLivraisonsParOrdreDePassage(),
							gestionnaire.getHoraireDebutTournee(), gestionnaire.getHoraireFinTournee());
					controleur.setEtatCourant(controleur.etatTourneeAffiche);	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else {
			controleur.gestionLivraisonsVue.afficherErreur("Erreur : Temps limite atteint !");
		}
	}

	public void clicBoutonHome(Controleur controleur, Gestionnaire gestionnaire)
	{
		gestionnaire.effacerTournee();
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
		gestionnaire.effacerLivraisonsEtEntrepot();
		if(controleur.stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/ChoixDemandeLivraisonsVue/ChoixDemandeLivraisons.fxml"));
				Parent root = fxmlLoader.load();
				controleur.choixDemandeLivraisonsVue = (ChoixDemandeLivraisonsVue) fxmlLoader.getController();
				controleur.choixDemandeLivraisonsVue.setControleur(controleur);
				Scene scene = new Scene(root);
				controleur.stage.setTitle("Itine'GO");
				controleur.stage.setScene(scene);
				controleur.stage.show();
				controleur.choixDemandeLivraisonsVue.dessinePlan(gestionnaire.getPlan());
				controleur.setEtatCourant(controleur.etatPlanVilleAffiche);	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void redessinerPlan(Controleur controleur, Gestionnaire gestionnaire)
	{
		controleur.gestionLivraisonsVue.dessinePlan(gestionnaire.getPlan());
	}
	
	public void getEtat()
	{
		System.out.println("etat livraisons affichées");
	}
	
	
}
