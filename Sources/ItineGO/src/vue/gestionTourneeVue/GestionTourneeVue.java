package vue.gestionTourneeVue;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import controleur.Controleur;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import modeles.Livraison;
import modeles.Noeud;
import modeles.Plan;
import vue.planVilleVue.PlanVilleVue;

public class GestionTourneeVue implements Initializable{
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
    private TableColumn<Livraison, String> arriveeColonne;
    
    @FXML
    private TableColumn<Livraison, String> departColonne;
    
	@FXML
	private Label labelEntrepot;
	
	@FXML
	private Label labelError;
	
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
//		labelError.setVisible(false);
		
        planVillePane.setStyle("-fx-background-color: rgb(240,237,230);-fx-border-color: grey;");
        
        planVilleVue = new PlanVilleVue(planVillePane.getPrefWidth(), planVillePane.getPrefHeight());
        planVillePane.getChildren().add(planVilleVue);
        
        final ChangeListener<Number> listener = new ChangeListener<Number>()
        {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				controleur.redessinner();
			}
          
        };
        planVillePane.widthProperty().addListener(listener);
        
        adresseColonne.setCellValueFactory(param -> { 
        	final Livraison livraison = param.getValue(); 
            return new SimpleStringProperty(String.valueOf(livraison.getNoeud().getId())); 
        }); 
        
        plageDebutColonne.setCellValueFactory(param -> { 
        	final Livraison livraison = param.getValue(); 
        	if(livraison.getDebutPlage() != null) {
        		return new SimpleStringProperty(livraison.getDebutPlage().toString()); 
        	} else {
        		return new SimpleStringProperty("-");
        	}
        }); 
        
        plageFinColonne.setCellValueFactory( param -> {
        	final Livraison livraison = param.getValue(); 
        	if(livraison.getFinPlage() != null) {
	        	return new SimpleStringProperty(livraison.getFinPlage().toString());
	        } else {
	    		return new SimpleStringProperty("-");
	    	}
        });
        
        arriveeColonne.setCellValueFactory( param -> {
        	return new SimpleStringProperty("-");
        });
        
        departColonne.setCellValueFactory( param -> {
        	return new SimpleStringProperty("-");
        });
        /*arriveeColonne.setCellValueFactory(param -> { 
        	final Livraison livraison = param.getValue(); 
            return new SimpleStringProperty(String.valueOf(livraison.getDuree())); 
        }); 
        
        departColonne.setCellValueFactory(param -> { 
        	final Livraison livraison = param.getValue(); 
            return new SimpleStringProperty(String.valueOf(livraison.getDuree())); 
        }); */
        
        labelError.setVisible(false);   
       
        imageViewAcceuilExited();
        imageViewPrecedentExited();
	}

	public void dessinePlan(Plan plan) {
		if(plan != null) {
			if(plan.getEntrepot() != null && plan.getEntrepot().getNoeud() != null) {
				labelEntrepot.setText(String.valueOf(plan.getEntrepot().getNoeud().getId()));
			}
			if(plan.getTournee() != null) {
				for(Map.Entry<Noeud, Livraison> l : plan.getLivraisons().entrySet()) {
					if(l != null && l.getKey() != null) {
						livraisonTable.getItems().add(l.getValue());
					}
				}
			}
			planVilleVue.setWidth(planVillePane.getWidth());
			planVilleVue.setHeight(planVillePane.getHeight());
			planVilleVue.dessinerPlan(plan);
		}
	}
	
	public void afficherErreur(String erreur) {
		labelError.setDisable(false);
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
}