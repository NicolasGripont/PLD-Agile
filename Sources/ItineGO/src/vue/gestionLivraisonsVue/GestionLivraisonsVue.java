package vue.gestionLivraisonsVue;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import controleur.Controleur;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import vue.gestionVue.GestionVue;
import vue.planVilleVue.PlanVilleVue;
import modeles.Livraison;
import modeles.LivraisonTournee;
import modeles.Noeud;
import modeles.Plan;

public class GestionLivraisonsVue extends GestionVue {
	private Controleur controleur;
	
	@FXML
    private TableView<Livraison> livraisonTable;
    
	@FXML
    private TableColumn<Livraison, String> adresseColonne;
    
    @FXML
    private TableColumn<Livraison, String> plageDebutColonne;
    
    @FXML
    private TableColumn<Livraison, String> plageFinColonne;
    
    @FXML
    private TableColumn<Livraison, String> dureeColonne;
    
	@FXML
	private Label labelEntrepot;
	
	@FXML
	private Label labelError;
	
	@FXML
	private Button boutonCalculer;
	
	@FXML
	private Button boutonStopCalculer;
	
	@FXML
	private ProgressBar barreChargement;
	
	@FXML
	private Label labelTempsRestant;
	
	@FXML 
	private StackPane planVillePane;
	
	private PlanVilleVue planVilleVue;
	
	@FXML
	private ImageView imageViewAccueil;
	
	@FXML
	private HBox boxStopperCalcule;
	
	@FXML
	private ImageView imageViewPrecedent;
	
	private final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	
	private Thread threadCalcul;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		boxStopperCalcule.setVisible(false);
		
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
        	final Livraison livraison = param.getValue(); 
            return new SimpleStringProperty(String.valueOf(livraison.getNoeud().getId())); 
        }); 
        
        plageDebutColonne.setCellValueFactory(param -> { 
        	final Livraison livraison = param.getValue(); 
        	if(livraison.getDebutPlage() != null && !livraison.getDebutPlage().toString().equals("0:0:0")) {
        		return new SimpleStringProperty(livraison.getDebutPlage().getHoraire()); 
        	} else {
        		return new SimpleStringProperty("-");
        	}
        }); 
        
        plageFinColonne.setCellValueFactory( param -> {
        	final Livraison livraison = param.getValue(); 
        	if(livraison.getFinPlage() != null && !livraison.getFinPlage().toString().equals("0:0:0")) {
	        	return new SimpleStringProperty(livraison.getFinPlage().getHoraire());
	        } else {
	    		return new SimpleStringProperty("-");
	    	}
        });
        
        dureeColonne.setCellValueFactory(param -> { 
        	final Livraison livraison = param.getValue(); 
            return new SimpleStringProperty(String.valueOf(livraison.getDuree())); 
        }); 
        
        labelError.setVisible(false);  

        imageViewAcceuilExited();
        imageViewPrecedentExited();
        
        livraisonTable.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				Livraison livraison = (Livraison) newValue;
				planVilleVue.livraisonSelected(livraison);
			}
          });
	}
	
	public void selectionneNoeud(Noeud noeud) {
		for(Livraison l : livraisonTable.getItems()) {
			if(l.getNoeud().equals(noeud)) {
				livraisonTable.getSelectionModel().select(l);
			}
		}
	}

	public void miseAJourTableau(Plan plan) {
		if(plan != null) {
			if(plan.getEntrepot() != null && plan.getEntrepot().getNoeud() != null) {
				labelEntrepot.setText(String.valueOf(plan.getEntrepot().getNoeud().getId()) + " - Début Livraison à " + 
						plan.getEntrepot().getHoraireDepart().getHoraire());
			}
			livraisonTable.getItems().clear();
			if(plan.getLivraisons() != null) {
				for(Map.Entry<Noeud, Livraison> l : plan.getLivraisons().entrySet()) {
					if(l != null && l.getKey() != null) {
						livraisonTable.getItems().add(l.getValue());
					}
				}
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
	public void calculerLivraisonAction() {
		boutonCalculer.setVisible(false);
		boxStopperCalcule.setVisible(true);
		barreChargement.setProgress(0);
		threadCalcul = new Thread() {
			public void run() {
				int tpsMax = controleur.getGestionnaire().getTempsMaxDeCalcul();
				int tps = 0;
				while(tps <= tpsMax) {
					Platform.runLater(() -> updateChrono(tps, tpsMax));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		threadCalcul.start();
		controleur.clicBoutonCalculerTournee();	
	}
	
	private void updateChrono(int tps, int tpsMax) {
		String tpsStr = "Temps restant : ";
		tps += 1;
		barreChargement.setProgress(new Double(tps) / new Double(tpsMax));
		tpsStr += (tpsMax-tps) + "s";
		labelTempsRestant.setText(tpsStr);
	}
	
	@FXML
	private void stopperCalculLivraisonAction() {
		boutonCalculer.setVisible(true);
		boxStopperCalcule.setVisible(false);
		controleur.clicBoutonsStopperCalculeTournee();
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
}
