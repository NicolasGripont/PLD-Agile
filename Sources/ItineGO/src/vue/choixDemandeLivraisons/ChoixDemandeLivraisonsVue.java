package vue.choixDemandeLivraisons;

import java.net.URL;
import java.util.ResourceBundle;

import controleur.Controleur;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ChoixDemandeLivraisonsVue implements Initializable{
	private Controleur controleur;
	
	@FXML
	private TextField textFieldLienFichier;
	
	@FXML
	private Button boutonParcourirFichier;
	
	@FXML
	private Button boutonValider;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public Controleur getControleur() {
		return controleur;
	}
	
	public void setControleur(Controleur controleur) {
		this.controleur = controleur;
	}
}
