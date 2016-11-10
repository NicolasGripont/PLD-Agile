package vue.gestionVue;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import modeles.Noeud;

/**
 * Classe abstraite qui definie une méthode selectionneNoeud
 * Méthode utilisé par exemple dans plan ville pour faire le lien entre l'IHM Plan et le tableau de la vue gestion 
 */
public abstract class GestionVue implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void selectionneNoeud(Noeud noeud) {

	}
}
