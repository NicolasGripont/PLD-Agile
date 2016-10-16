package vue.GlisserDeposerFichierVue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * Cette classe définit un StackPane permettant de glisser-déposer un fichier.
 * On peut définir des extensions accpetés (par défaut toutes).
 * @author Nico
 *
 */
public class GlisserDeposerFichierVue extends StackPane {
	
	private File fichierChoisie = null;	
	
	/**
	 * Liste des extensions acceptées pour le fichier. Si vide, tout fichier est accepté.
	 * Une extension doit commener par un point (ex: '.xml')
	 */
	private final List<String> extensionsAcceptees = new ArrayList<>();
	
	/**
	 * Handler déclanché si le fichier glissé-déposé est accepté.
	 */
	private EventHandler<ActionEvent> fichierAccepteAction;
	
	/**
	 * Handler déclanché si le fichier glissé-déposé est refusé.
	 */
	private EventHandler<ActionEvent> fichierRefuseAction;
	
	public GlisserDeposerFichierVue(String message) {
		AnchorPane.setBottomAnchor(this, 0.0);
		AnchorPane.setTopAnchor(this, 0.0);
		AnchorPane.setRightAnchor(this, 0.0);
		AnchorPane.setLeftAnchor(this, 0.0);
		
		Label label= new Label(message);
		this.getChildren().add(label);
		
		this.setStyle("-fx-background-color: #EEEEEE;"
				+ "-fx-border-color: #C6C6C6;;"
				+ "-fx-border-radius: 3px;");
		
		this.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(final DragEvent event) {
                mouseDragOver(event);
            }
        });
 
		this.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(final DragEvent event) {
                mouseDragDropped(event);
            }
        });
 
		this.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(final DragEvent event) {
            	setStyle("-fx-border-color: #C6C6C6;"
            			+ "-fx-border-radius: 3px;");
            }
        });
		
	}
	
	public File getFichierChoisie() {
		return fichierChoisie;
	}



	public void setFichierAccepteAction(EventHandler<ActionEvent> fichierAccepteAction) {
		this.fichierAccepteAction = fichierAccepteAction;
	}



	public void setFichierRefuseAction(EventHandler<ActionEvent> fichierRefuseAction) {
		this.fichierRefuseAction = fichierRefuseAction;
	}

	/**
	 * Ajoute extension à la liste des extensions acceptées
	 * @param extension : extension à ajouter à la liste des extensions acceptées. L'extension doit commener par un point (ex: '.xml')
	 */
	public void addExtensionAcceptee(String extension) {
		extensionsAcceptees.add(extension);
	}
	
	/**
	 * Supprime la première occurrence de extension
	 * @param extension : extension à supprimer de la liste des extensions acceptées
	 */
	public void removeExtensionAcceptee(String extension) {
		int index = -1;
		for(int i=0; i< extensionsAcceptees.size(); i++) {
			if(extension.equals(extensionsAcceptees.get(i))){
				index = i;
				break;
			}
		}
		if(index != -1) {
			extensionsAcceptees.remove(index);
		}
	}
	
	/**
	 * Supprime toutes les extensions de la liste des extensions acceptées. Tout fichier est alors accepté.
	 */
	public void removeAllExtensionsAcceptees() {
		extensionsAcceptees.clear();
	}

	public boolean estFichierAccepte(File fichier) {
		if(extensionsAcceptees.isEmpty()){
			return true;
		}
		
		String name = fichier.getName().toLowerCase();
		boolean accepte = false;
        for(String extension : extensionsAcceptees) {
        	if(name.endsWith(extension)) {
        		accepte = true;
        		break;
        	}
        }
        return accepte;
	}
	
	private void mouseDragDropped(final DragEvent e) {
        final Dragboard db = e.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            // On récupère seulement le premier fichier glissé-déposé
            File fichier = db.getFiles().get(0);
            if(estFichierAccepte(fichier)) {
            	fichierChoisie = fichier;
            } else {
            	fichierChoisie = null;
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                	if(fichierChoisie != null) {
                		fichierAccepteAction.handle(null);
                	} else {
                		fichierRefuseAction.handle(null);
                	}
                }
            });
        }
        e.setDropCompleted(success);
        e.consume();
    }
 
    private void mouseDragOver(final DragEvent e) {
        final Dragboard db = e.getDragboard();
 
        final boolean isAccepted = db.getFiles().get(0).getName().toLowerCase().endsWith(".xml");
 
        if (db.hasFiles()) {
            if (isAccepted) {
                this.setStyle("-fx-border-color: green;"
              + "-fx-border-width: 5;"
              + "-fx-background-color: #C6C6C6;"
              + "-fx-border-style: solid;"
              + "-fx-border-radius: 1px;");
                e.acceptTransferModes(TransferMode.COPY);
            } else {
                this.setStyle("-fx-border-color: red;"
              + "-fx-border-width: 5;"
              + "-fx-background-color: #C6C6C6;"
              + "-fx-border-style: solid;"
              + "-fx-border-radius: 1px;");
                e.acceptTransferModes(TransferMode.COPY);
            }
        } else {
        	e.consume();
        }
    }
	
}
