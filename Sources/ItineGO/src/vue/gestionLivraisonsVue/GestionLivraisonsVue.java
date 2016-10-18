package vue.gestionLivraisonsVue;

import java.net.URL;
import java.util.ResourceBundle;
import controleur.Controleur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import modeles.Plan;
import vue.planVilleVue.PlanVilleVue;

public class GestionLivraisonsVue implements Initializable{
	private Controleur controleur;
	private Plan plan;
	
	@FXML
	private Label labelEntrepot;
	
	@FXML
	private Label labelError;
	
	@FXML
	private Button boutonCalculer;
	
	@FXML 
	private ScrollPane planVilleScrollPane;
	
	private PlanVilleVue planVilleVue;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		labelError.setVisible(false);
		
		planVilleScrollPane.setStyle("-fx-background-color: rgb(240,237,230);-fx-border-color: grey;");
        double size = Math.max(planVilleScrollPane.getPrefWidth(),planVilleScrollPane.getPrefHeight()) - 20;
        planVilleVue = new PlanVilleVue(size, size);
        planVilleScrollPane.setContent(planVilleVue);
        
        
		final ChangeListener<Number> listener = new ChangeListener<Number>()
        {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
		        double size = Math.max(planVilleScrollPane.getWidth(),planVilleScrollPane.getHeight()) - 20;
		        if(size + 20 > Math.max(planVilleScrollPane.getPrefWidth(),planVilleScrollPane.getPrefHeight()) - 20)
		        planVilleVue.resize(size,size);
				planVilleVue.dessinePlan(plan);
			}
        };
        planVilleScrollPane.widthProperty().addListener(listener);
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
        planVilleVue.dessinePlan(plan);
	}
	
	@FXML
	public void precedent(){
		controleur.getChoixDemandeLivraisonVue().getPlan().effacerTournee();
		controleur.showChoixDemandeLivraison();
	}
	
	@FXML
	public void calculerLivraisonAction() {
		plan.CalculDeTournee();
		planVilleVue.dessinePlan(plan);
	}
}
