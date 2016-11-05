package vue.gestionTourneeVue;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import controleur.Controleur;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import modeles.Horaire;
import modeles.LivraisonTournee;
import modeles.Noeud;
import modeles.Plan;
import vue.gestionVue.GestionVue;
import vue.planVilleVue.PlanVilleVue;

public class GestionTourneeVue extends GestionVue{
	private Controleur controleur;
	
	@FXML
    private TableView<LivraisonTournee> livraisonTable;
    
	@FXML
    private TableColumn<LivraisonTournee, String> adresseColonne;
    
    @FXML
    private TableColumn<LivraisonTournee, String> plageDebutColonne;
    
    @FXML
    private TableColumn<LivraisonTournee, String> plageFinColonne;
    
    @FXML
    private TableColumn<LivraisonTournee, String> arriveeColonne;
    
    @FXML
    private TableColumn<LivraisonTournee, String> departColonne;
    
    @FXML
    private TableColumn<LivraisonTournee, String> dureeColonne;
    
	@FXML
	private Label labelEntrepot;
	
	@FXML
	private Label labelError;
	
	@FXML
	private Label labelInstruction;
	
	@FXML 
	private StackPane planVillePane;
	
	private PlanVilleVue planVilleVue;
	
	@FXML
	private ImageView imageViewAccueil;
	
	@FXML
	private ImageView imageViewPrecedent;
	
	private final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub        
        planVilleVue = new PlanVilleVue(planVillePane.getPrefWidth(), planVillePane.getPrefHeight(), this);
        planVillePane.getChildren().add(planVilleVue);
        
        final ChangeListener<Number> listener = new ChangeListener<Number>()
        {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				controleur.redessinerPlan();
			}
          
        };
        planVillePane.widthProperty().addListener(listener);
        planVillePane.heightProperty().addListener(listener);
        
        adresseColonne.setCellValueFactory(param -> { 
        	final LivraisonTournee livraison = param.getValue(); 
            return new SimpleStringProperty(String.valueOf(livraison.getLivraison().getNoeud().getId())); 
        }); 
        
        plageDebutColonne.setCellValueFactory(param -> { 
        	final LivraisonTournee livraison = param.getValue(); 
        	if(livraison.getLivraison().getDebutPlage() != null && !livraison.getLivraison().getDebutPlage().getHoraire().equals("00:00")) {
        		return new SimpleStringProperty(livraison.getLivraison().getDebutPlage().getHoraire()); 
        	} else {
        		return new SimpleStringProperty("-");
        	}
        }); 
        
        plageFinColonne.setCellValueFactory( param -> {
        	final LivraisonTournee livraison = param.getValue(); 
        	if(livraison.getLivraison().getFinPlage() != null && !livraison.getLivraison().getFinPlage().getHoraire().equals("00:00")) {
	        	return new SimpleStringProperty(livraison.getLivraison().getFinPlage().getHoraire());
	        } else {
	    		return new SimpleStringProperty("-");
	    	}
        });
        
        arriveeColonne.setCellValueFactory(param -> { 
        	final LivraisonTournee livraison = param.getValue(); 
            return new SimpleStringProperty(livraison.getHeureArrive().getHoraire()); 
        }); 
        
        departColonne.setCellValueFactory(param -> { 
        	final LivraisonTournee livraison = param.getValue(); 
            return new SimpleStringProperty(livraison.getHeureDepart().getHoraire()); 
        });
        
        dureeColonne.setCellValueFactory(param -> { 
        	final LivraisonTournee livraison = param.getValue(); 
            return new SimpleStringProperty(String.valueOf(livraison.getLivraison().getDuree())); 
        });
        
        labelError.setVisible(false);   
        labelInstruction.setVisible(false);
        
        imageViewAcceuilExited();
        imageViewPrecedentExited();
        
        livraisonTable.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				LivraisonTournee livraison = (LivraisonTournee) newValue;
				planVilleVue.livraisonSelected(livraison.getLivraison());
			}
          });
	}
	
	public void solutionOptimale(boolean optimale) {
		if(optimale) {
			labelError.setVisible(true);
			labelError.setStyle("-fx-text-fill : green;");
			labelError.setText("La solution est optimale");
		} else {
			labelError.setVisible(true);
			labelError.setStyle("-fx-text-fill : blue;");
			labelError.setText("La solution n'est pas optimale");
		}
	}
	
	public void selectionneNoeud(Noeud noeud) {
		for(LivraisonTournee t : livraisonTable.getItems()) {
			if(t.getLivraison().getNoeud().equals(noeud)) {
				livraisonTable.getSelectionModel().select(t);
			}
		}
	}

	public void miseAJourTableau(List<LivraisonTournee> list, Horaire horaireDebut, Horaire horaireFin) {
		labelEntrepot.setText("Début de la tournée : " + horaireDebut.getHoraire() + " - Fin de la tournée : " + horaireFin.getHoraire());
		if(list != null && list.size() > 0) {
			for(LivraisonTournee l : list) {
				livraisonTable.getItems().add(l);
			}
		}
	}
	
	public void dessinePlan(Plan plan) {
		if(plan != null) {
			planVilleVue.setWidth(planVillePane.getWidth());
			planVilleVue.setHeight(planVillePane.getHeight());
			planVilleVue.dessinerPlan(plan);
		}
	}
	
	public void afficherErreur(String erreur) {
		labelError.setVisible(true);
		labelError.setText(erreur);
	}

	public Controleur getControleur() {
		return controleur;
	}

	public void setControleur(Controleur controleur) {
		this.controleur = controleur;
	}
	
	@FXML
	public void home() {
		controleur.clicBoutonHome();
	}
	
	@FXML
	public void precedent(){
		controleur.clicBoutonRetour();
	}
	
	@FXML
	private void imageViewPrecedentEntered() {
        imageViewPrecedent.setImage(new Image(classLoader.getResource("precedent_bleu.png").toString()));
	}
	
	@FXML
	private void imageViewPrecedentExited() {
        imageViewPrecedent.setImage(new Image(classLoader.getResource("precedent_noir.png").toString()));
	}
	
	@FXML
	private void imageViewAccueilEntered() {
        imageViewAccueil.setImage(new Image(classLoader.getResource("accueil_bleu.png").toString()));
	}
	
	@FXML
	private void imageViewAcceuilExited() {
		imageViewAccueil.setImage(new Image(classLoader.getResource("accueil_noir.png").toString()));
	}
	
	@FXML
	private void genererFeuilleDeRoute() {
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Générer feuille de route");
        File file = fileChooser.showSaveDialog(controleur.getStage());
        if (file != null) {
            controleur.clicBoutonGenererFeuilleDeRoute(file.getAbsolutePath());
            labelError.setVisible(true);
            labelError.setStyle("-fx-text-fill : green;");
            labelError.setText("Feuille de route générée");
        }
	}
}