package vue.choixPlanVille;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import controleur.Controleur;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class ChoixPlanVilleVue implements Initializable{
	private Controleur controleur;
	private File fichierChoisie = null;
	
	@FXML
	private TextField textFieldLienFichier;
	
	@FXML
	private Label labelError;
	
	@FXML
	private Button boutonParcourirFichier;
	
	@FXML
	private Button boutonValider;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	public Controleur getControleur() {
		return controleur;
	}
	
	public void setControleur(Controleur controleur) {
		this.controleur = controleur;
	}
	
	public File getFichierChoisi() {
		return fichierChoisie;
	}
	
	public void afficherErreur(String erreur) {
		labelError.setDisable(false);
		labelError.setText(erreur);
	}
	
	@FXML
	public void choixFichierAction(MouseEvent event) {
		System.out.println("Hello world!");
		FileChooser dialogue = new FileChooser();
		dialogue.showOpenDialog(controleur.getStage());
		fichierChoisie = dialogue.getSelectedFile();
	}
	
	@FXML
	public void validerAction(MouseEvent event) {
		if(controleur == null) {
			labelError.setDisable(false);
			labelError.setText("Erreur : Controleur is null, fatal error");
		} else if(fichierChoisie == null) {
			labelError.setDisable(false);
			labelError.setText("Erreur : Le fichier choisi est invalide");
		}
	}
}
