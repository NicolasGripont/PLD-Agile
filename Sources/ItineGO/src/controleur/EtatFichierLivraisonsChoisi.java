package controleur;

import java.io.File;
import java.io.IOException;
import exceptions.BadXmlFile;
import exceptions.BadXmlLivraison;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import modeles.Gestionnaire;
import vue.choixPlanVilleVue.ChoixPlanVilleVue;
import vue.gestionLivraisonsVue.GestionLivraisonsVue;

public class EtatFichierLivraisonsChoisi extends EtatDefaut {

	public void clicBoutonValider(Gestionnaire gestionnaire, Controleur controleur, File fichierXML)
	{
		try {
			gestionnaire.chargerLivraisons(fichierXML);
			if(controleur.stage != null) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/GestionLivraisonsVue/GestionLivraisonsVue.fxml"));
					Parent root = fxmlLoader.load();
					controleur.gestionLivraisonsVue = (GestionLivraisonsVue) fxmlLoader.getController();
					controleur.gestionLivraisonsVue.setControleur(controleur);
					controleur.stage.setTitle("Itine'GO");
					Scene scene = new Scene(root, controleur.stage.getWidth(), controleur.stage.getHeight());
					controleur.stage.setScene(scene);
					controleur.stage.show();
					controleur.gestionLivraisonsVue.dessinePlan(gestionnaire.getPlan());
					controleur.gestionLivraisonsVue.miseAJourTableau(gestionnaire.getPlan());
					controleur.setEtatCourant(controleur.etatLivraisonsAffichees);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch(BadXmlFile exception) {
			controleur.choixDemandeLivraisonsVue.afficherErreur("Erreur : Fichier XML mal formé");
			controleur.setEtatCourant(controleur.etatPlanVilleAffiche);
		} catch(BadXmlLivraison exception) {
			controleur.choixDemandeLivraisonsVue.afficherErreur("Erreur : Impossible de parser les livraisons");
			controleur.setEtatCourant(controleur.etatPlanVilleAffiche);
		}
	}
	
	public void glisserDeposer(Controleur controleur, boolean accepte, File fichier)
	{
		if(accepte) {
			controleur.choixDemandeLivraisonsVue.fichierAccepte(fichier);
		} else {
			controleur.choixDemandeLivraisonsVue.fichierRefuse();
			//changement d'état
			controleur.setEtatCourant(controleur.etatPlanVilleAffiche);
		}
	}
	
	public void clicBoutonParcourir(Controleur controleur, boolean accepte, File fichier)
	{
		if(accepte) {
			controleur.choixDemandeLivraisonsVue.fichierAccepte(fichier);
		} else {
			controleur.choixDemandeLivraisonsVue.fichierRefuse();
			//changement d'état
			controleur.setEtatCourant(controleur.etatPlanVilleAffiche);
		}
	}
	
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
				Scene scene = new Scene(root, controleur.stage.getWidth(), controleur.stage.getHeight());
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
	
	public void redessinerPlan(Controleur controleur, Gestionnaire gestionnaire)
	{
		controleur.choixDemandeLivraisonsVue.dessinePlan(gestionnaire.getPlan());
	}
	
	public void getEtat()
	{
		System.out.println("etat fichier livraison choisi");
	}
	
}
