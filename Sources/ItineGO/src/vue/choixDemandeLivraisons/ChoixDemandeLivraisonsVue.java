package vue.choixDemandeLivraisons;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import controleur.Controleur;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import modeles.Plan;
import vue.PlanVilleVue.PlanVilleVue;

public class ChoixDemandeLivraisonsVue implements Initializable{
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
	private StackPane contentPane;
	
	@FXML 
	private Pane planVillePane;
	
	private PlanVilleVue planVilleVue;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contentPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(final DragEvent event) {
                mouseDragOver(event);
            }
        });
 
        contentPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(final DragEvent event) {
                mouseDragDropped(event);
            }
        });
 
        contentPane.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(final DragEvent event) {
                contentPane.setStyle("-fx-border-color: #C6C6C6;");
            }
        });
        
        labelError.setVisible(false);
        planVilleVue = new PlanVilleVue(planVillePane.getMinWidth(),planVillePane.getMinHeight());
        planVillePane.getChildren().add(planVilleVue);
	}
	
	public void dessinePlanVille(Plan plan) {
		planVilleVue.dessinePlan(plan);
	}
	
	public void afficherErreur(String erreur) {
		labelError.setDisable(false);
		labelError.setText(erreur);
	}
	
	@FXML
	public void choixFichierAction(MouseEvent event) {
		FileChooser dialogue = new FileChooser();
		FileChooser.ExtensionFilter extensionsFilter = new FileChooser.ExtensionFilter("Fichier XML","*.xml");
		dialogue.setSelectedExtensionFilter(extensionsFilter);
		File tmp = dialogue.showOpenDialog(controleur.getStage());
		if(tmp.getName().toLowerCase().endsWith(".xml")) {
        	fichierChoisie = tmp;
        } else {
        	fichierChoisie = null;
        }
		if(fichierChoisie != null) {
			labelError.setVisible(false);
			textFieldLienFichier.setText(fichierChoisie.getPath());
		} else {
			labelError.setVisible(true);
			labelError.setText("Erreur : Le fichier choisi est invalide");
		}
	}
	
	@FXML
	public void validerAction(MouseEvent event) {
		if(controleur == null) {
			labelError.setVisible(true);
			labelError.setText("Erreur : Controler is null, fatal error");
		} else if(fichierChoisie == null) {
			labelError.setVisible(true);
			labelError.setText("Erreur : Le fichier choisi est invalide");
		}
	}
	
	private void mouseDragDropped(final DragEvent e) {
        final Dragboard db = e.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            // Only get the first file from the list
            
            if(db.getFiles().get(0).getName().toLowerCase().endsWith(".xml")) {
            	fichierChoisie = db.getFiles().get(0);
            } else {
            	fichierChoisie = null;
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                	if(fichierChoisie != null) {
                		labelError.setVisible(false);
                		textFieldLienFichier.setText(fichierChoisie.getAbsolutePath());
                	} else {
                		textFieldLienFichier.setText("");
                		labelError.setVisible(true);
            			labelError.setText("Erreur : Le fichier choisi est invalide");
                	}
                }
            });
        }
        e.setDropCompleted(success);
        e.consume();
    }
 
    private  void mouseDragOver(final DragEvent e) {
        final Dragboard db = e.getDragboard();
 
        final boolean isAccepted = db.getFiles().get(0).getName().toLowerCase().endsWith(".xml");
 
        if (db.hasFiles()) {
            if (isAccepted) {
                contentPane.setStyle("-fx-border-color: green;"
              + "-fx-border-width: 5;"
              + "-fx-background-color: #C6C6C6;"
              + "-fx-border-style: solid;");
                e.acceptTransferModes(TransferMode.COPY);
            } else {
                contentPane.setStyle("-fx-border-color: red;"
              + "-fx-border-width: 5;"
              + "-fx-background-color: #C6C6C6;"
              + "-fx-border-style: solid;");
                e.acceptTransferModes(TransferMode.COPY);
            }
        } else {
        	e.consume();
        }
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
}
