package controleur;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import modeles.Gestionnaire;
import vue.choixDemandeLivraisonsVue.ChoixDemandeLivraisonsVue;

public class EtatPlanVilleChoisie extends EtatDefaut {

	public void clicBoutonValider(Gestionnaire gestionnaire, Controleur controleur, File fichierXML)
	{
		//modification du modèle
		if(gestionnaire.chargerPlan(fichierXML)) {
			//modification des vues
			if(controleur.stage != null) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/ChoixDemandeLivraisonsVue/ChoixDemandeLivraisons.fxml"));
					Parent root = fxmlLoader.load();
					controleur.choixDemandeLivraisonsVue = (ChoixDemandeLivraisonsVue) fxmlLoader.getController();
					controleur.choixDemandeLivraisonsVue.setControleur(controleur);
					Scene scene = new Scene(root, controleur.stage.getWidth(), controleur.stage.getHeight());
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
		} else {
			controleur.choixPlanVilleVue.afficherErreur("Erreur : impossible de parser le fichier");
			controleur.setEtatCourant(controleur.etatApplicationDemarree);
		}
	}
	
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
