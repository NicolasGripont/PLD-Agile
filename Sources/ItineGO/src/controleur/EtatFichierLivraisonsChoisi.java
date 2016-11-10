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

/**
 * Etat après choix du fichier de livraison
 */
public class EtatFichierLivraisonsChoisi extends EtatDefaut {

	/**
	 * Permet de valider le fichier pris en compte et de passer à la vue
	 * suivante où les livraisons sont affichées. Si le fichier n'est pas bon on
	 * reste en l'état, sinon on passe a l'état EtatLivraisonsAffichees.
	 * 
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param fichierXML
	 *            : Fichier xml pris en compte.
	 */
	@Override
	public void clicBoutonValider(Gestionnaire gestionnaire, Controleur controleur, File fichierXML) {
		try {
			gestionnaire.chargerLivraisons(fichierXML);
			if (controleur.stage != null) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(
							this.getClass().getResource("/vue/GestionLivraisonsVue/GestionLivraisonsVue.fxml"));
					Parent root = fxmlLoader.load();
					controleur.gestionLivraisonsVue = (GestionLivraisonsVue) fxmlLoader.getController();
					controleur.gestionLivraisonsVue.setControleur(controleur);
					controleur.stage.setTitle("Itine'GO");
					Scene scene = new Scene(root, controleur.stage.getScene().getWidth(),
							controleur.stage.getScene().getHeight());
					controleur.stage.setScene(scene);
					controleur.stage.show();
					controleur.gestionLivraisonsVue.dessinePlan(gestionnaire.getPlan());
					controleur.gestionLivraisonsVue.miseAJourTableau(gestionnaire.getPlan());
					controleur.setEtatCourant(controleur.etatLivraisonsAffichees);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (BadXmlFile exception) {
			controleur.choixDemandeLivraisonsVue.afficherErreur(exception.getMessage());
			controleur.setEtatCourant(controleur.etatPlanVilleAffiche);
		} catch (BadXmlLivraison exception) {
			controleur.choixDemandeLivraisonsVue.afficherErreur(exception.getMessage());
			controleur.setEtatCourant(controleur.etatPlanVilleAffiche);
		}
	}

	/**
	 * Permet de faire un glisser deposer d'un fichier, si le fichier est
	 * accepté on reste en l'état sinon on revient à l'état
	 * EtatPlanVilleAffiche. On va indiquer à la vue si le fichier est accepté
	 * ou non et celle-ci affichera un message correspondant.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param accepte
	 *            : Indique si le fichier a été accepté ou non.
	 * @param fichier
	 *            : Fichier pris en compte.
	 */
	@Override
	public void glisserDeposer(Controleur controleur, boolean accepte, File fichier) {
		if (accepte) {
			controleur.choixDemandeLivraisonsVue.fichierAccepte(fichier);
		} else {
			controleur.choixDemandeLivraisonsVue.fichierRefuse();
			controleur.setEtatCourant(controleur.etatPlanVilleAffiche);
		}
	}

	/**
	 * Permet de choisir un fichier, si le fichier est accepté on reste en
	 * l'état sinon on revient à l'état EtatPlanVilleAffiche. On va indiquer à
	 * la vue si le fichier est accepté ou non et celle-ci affichera un message
	 * correspondant.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param accepte
	 *            : Indique si le fichier a été accepté ou non.
	 * @param fichier
	 *            : Fichier pris en compte.
	 */
	@Override
	public void clicBoutonParcourir(Controleur controleur, boolean accepte, File fichier) {
		if (accepte) {
			controleur.choixDemandeLivraisonsVue.fichierAccepte(fichier);
		} else {
			controleur.choixDemandeLivraisonsVue.fichierRefuse();
			controleur.setEtatCourant(controleur.etatPlanVilleAffiche);
		}
	}

	/**
	 * Efface tous les noeuds et tronçons du plan chargé puis retourne à la vue
	 * choixPlanVilleVue. On met l'état courant à l'état
	 * EtatApplicationDemarree.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 */
	@Override
	public void clicBoutonRetour(Controleur controleur, Gestionnaire gestionnaire) {
		gestionnaire.effacerNoeudsEtTroncons();
		if (controleur.stage != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						this.getClass().getResource("/vue/choixPlanVilleVue/ChoixPlanVille.fxml"));
				Parent root;
				root = fxmlLoader.load();
				controleur.choixPlanVilleVue = (ChoixPlanVilleVue) fxmlLoader.getController();
				controleur.choixPlanVilleVue.setControleur(controleur);
				Scene scene = new Scene(root, controleur.stage.getScene().getWidth(),
						controleur.stage.getScene().getHeight());
				controleur.stage.setTitle("Itine'GO");
				controleur.stage.setScene(scene);
				controleur.stage.show();
				controleur.setEtatCourant(controleur.etatApplicationDemarree);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * Permet de redessiner le plan dans la vue correspondante.
	 * 
	 * @param controleur
	 *            : Controleur de l'application.
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 */
	@Override
	public void redessinerPlan(Controleur controleur, Gestionnaire gestionnaire) {
		controleur.choixDemandeLivraisonsVue.dessinePlan(gestionnaire.getPlan());
	}

	@Override
	public void getEtat() {
		System.out.println("etat fichier livraison choisi");
	}

}
