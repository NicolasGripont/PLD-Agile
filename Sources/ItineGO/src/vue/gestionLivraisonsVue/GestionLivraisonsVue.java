package vue.gestionLivraisonsVue;

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
import javafx.scene.layout.StackPane;
import modeles.Plan;
import vue.planVilleVue.PlanVilleVue;
import modeles.Livraison;
import modeles.Noeud;

public class GestionLivraisonsVue implements Initializable{
	private Controleur controleur;
	private Plan plan;
	
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
	private StackPane planVillePane;
	
	private PlanVilleVue planVilleVue;
	
	
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
				dessinePlan();
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
        
        dureeColonne.setCellValueFactory(param -> { 
        	final Livraison livraison = param.getValue(); 
            return new SimpleStringProperty(String.valueOf(livraison.getDuree())); 
        }); 
        
        labelError.setVisible(false);    
	}

	public void dessinePlan() {
		planVilleVue.setWidth(planVillePane.getWidth());
		planVilleVue.setHeight(planVillePane.getHeight());
		planVilleVue.dessinerPlan(plan);
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

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
		if(this.plan.getEntrepot() != null && this.plan.getEntrepot().getNoeud() != null) {
			labelEntrepot.setText(String.valueOf(plan.getEntrepot().getNoeud().getId()));
		}
		if(this.plan.getLivraisons() != null) {
			for(Map.Entry<Noeud, Livraison> l : this.plan.getLivraisons().entrySet()) {
				if(l != null && l.getKey() != null) {
					livraisonTable.getItems().add(l.getValue());
				}
			}
		}
        planVilleVue.dessinerPlan(plan);
	}
	
	@FXML
	public void home() {
		//TODO : controleur.clicBoutonHome();
		controleur.getChoixDemandeLivraisonVue().getPlan().effacerTournee();
		controleur.showChoixDemandeLivraison();
	}
	
	@FXML
	public void precedent(){
		//TODO : controleur.clicBoutonRetour();
		controleur.getChoixDemandeLivraisonVue().getPlan().effacerTournee();
		controleur.showChoixDemandeLivraison();
	}
	
	@FXML
	public void calculerLivraisonAction() {
		//TODO : controleur.clicBoutonCalculerTournee();
		plan.CalculDeTournee();
		planVilleVue.dessinerPlan(plan);
	}
}
