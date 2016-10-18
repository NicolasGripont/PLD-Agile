package vue.gestionLivraisonsVue;

import java.net.URL;
import java.util.ResourceBundle;
import controleur.Controleur;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	private Pane planVillePane;
	
	private PlanVilleVue planVilleVue;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		labelError.setVisible(false);
		
		planVillePane.setStyle("-fx-background-color: rgb(240,237,230);-fx-border-color: grey;");
        planVilleVue = new PlanVilleVue(planVillePane.getMinWidth(),planVillePane.getMinHeight());
        planVillePane.getChildren().add(planVilleVue);
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
        planVilleVue.dessinerPlan(plan);
	}
	
	@FXML
	public void precedent(){
		controleur.getChoixDemandeLivraisonVue().getPlan().effacerTournee();
		controleur.showChoixDemandeLivraison();
	}
	
	@FXML
	public void calculerLivraisonAction() {
		plan.CalculDeTournee();
		planVilleVue.dessinerPlan(plan);
	}
}
