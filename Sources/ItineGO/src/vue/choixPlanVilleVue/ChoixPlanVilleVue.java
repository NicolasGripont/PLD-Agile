package vue.choixPlanVilleVue;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import vue.glisserDeposerFichierVue.GlisserDeposerFichierVue;

/**
 * Vue du choix du fichier plan
 * C'est la vue d'entrée de l'application
 */
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
	
	@FXML 
	private AnchorPane glisserDeposerFichierPane;
	
	private GlisserDeposerFichierVue glisserDeposerFichierVue;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		glisserDeposerFichierVue = new GlisserDeposerFichierVue("Glisser-Déposer la demande de plan.");
		glisserDeposerFichierPane.getChildren().add(glisserDeposerFichierVue);
		glisserDeposerFichierVue.addExtensionAcceptee(".xml");
		
		glisserDeposerFichierVue.setFichierAccepteAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				fichierGlisserDeposerAccepteAction();
			}
		});
		
		glisserDeposerFichierVue.setFichierRefuseAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				fichierGlisserDeposerRefuseAction();
			}
		});
		
        labelError.setVisible(false);
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
		labelError.setStyle("-fx-text-fill : red;");
		labelError.setVisible(true);
		labelError.setText(erreur);
	}
	
	@FXML
	public void choixFichierAction(MouseEvent event) {
		FileChooser dialogue = new FileChooser();
		FileChooser.ExtensionFilter extensionsFilter = new FileChooser.ExtensionFilter("Fichier XML","*.xml");
		dialogue.setSelectedExtensionFilter(extensionsFilter);
		File tmp = dialogue.showOpenDialog(controleur.getStage());
		if(tmp != null && tmp.getName().toLowerCase().endsWith(".xml")) {
			controleur.clicBoutonParcourir(true, tmp);
        } else {
    		controleur.clicBoutonParcourir(false, null);
        }
	}
	
	@FXML
	public void validerAction(MouseEvent event) {
		if(controleur == null) {
			labelError.setVisible(true);
			labelError.setStyle("-fx-text-fill : red;");
			labelError.setText("Erreur : Controler is null, fatal error");
		} else if(fichierChoisie == null) {
			labelError.setVisible(true);
			labelError.setStyle("-fx-text-fill : red;");
			labelError.setText("Erreur : Aucun fichier choisi");
		} else {
			controleur.clicBoutonValider(fichierChoisie);
		}
	}
	
	public void fichierGlisserDeposerAccepteAction() {
		controleur.glisserDeposer(true, glisserDeposerFichierVue.getFichierChoisie());
	}
	
	public void fichierGlisserDeposerRefuseAction() {
		controleur.glisserDeposer(false, null);
	}
    
	public void fichierAccepte(File fichier) {
		labelError.setVisible(false);
		fichierChoisie = fichier;
		textFieldLienFichier.setText(fichierChoisie.getAbsolutePath());
		labelError.setVisible(true);
		labelError.setStyle("-fx-text-fill : green;");
		labelError.setText("Votre fichier a été pris en compte.");
		glisserDeposerFichierVue.getLabel().setText("Glisser-Déposer une autre demande de plan.");
	}
	
	public void fichierRefuse(){
		labelError.setVisible(false);
		fichierChoisie = null;
		textFieldLienFichier.setText("");
		labelError.setVisible(true);
		labelError.setStyle("-fx-text-fill : red;");
		labelError.setText("Erreur : Le fichier choisi est invalide.");
	}
}
