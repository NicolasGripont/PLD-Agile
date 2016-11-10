package vue.choixDemandeLivraisonsVue;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import controleur.Controleur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import modeles.Plan;
import vue.glisserDeposerFichierVue.GlisserDeposerFichierVue;
import vue.planVilleVue.PlanVilleVue;

/**
 * Vue du choix du fichier de livraison Le plan de la ville est affiché
 */
public class ChoixDemandeLivraisonsVue implements Initializable {
	private Controleur controleur;
	private File fichierChoisie = null;

	@FXML
	private TextField textFieldLienFichier;

	@FXML
	private Label labelError;

	@FXML
	private ImageView imageViewPrecedent;

	@FXML
	private Button boutonParcourirFichier;

	@FXML
	private Button boutonValider;

	@FXML
	private StackPane planVillePane;

	private PlanVilleVue planVilleVue;

	@FXML
	private AnchorPane glisserDeposerFichierPane;

	private GlisserDeposerFichierVue glisserDeposerFichierVue;

	private final Tooltip tooltipPrecedent = new Tooltip("Retour au choix du plan");

	private final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.textFieldLienFichier.setEditable(false);
		this.glisserDeposerFichierVue = new GlisserDeposerFichierVue("Glisser-Déposer la demande de livraison.");
		this.glisserDeposerFichierPane.getChildren().add(this.glisserDeposerFichierVue);
		this.glisserDeposerFichierVue.addExtensionAcceptee(".xml");

		this.glisserDeposerFichierVue.setFichierAccepteAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				ChoixDemandeLivraisonsVue.this.fichierGlisserDeposerAccepteAction();
			}
		});

		this.glisserDeposerFichierVue.setFichierRefuseAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				ChoixDemandeLivraisonsVue.this.fichierGlisserDeposerRefuseAction();
			}
		});

		this.labelError.setVisible(false);

		this.planVilleVue = new PlanVilleVue(this.planVillePane.getPrefWidth(), this.planVillePane.getPrefHeight());
		this.planVillePane.getChildren().add(this.planVilleVue);

		final ChangeListener<Number> listener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				ChoixDemandeLivraisonsVue.this.controleur.redessinerPlan();
			}

		};
		this.planVillePane.widthProperty().addListener(listener);
		this.planVillePane.heightProperty().addListener(listener);

		this.imageViewPrecedentExited();
		Tooltip.install(this.imageViewPrecedent, this.tooltipPrecedent);
	}

	public void dessinePlan(Plan plan) {
		if (plan != null) {
			this.planVilleVue.setWidth(this.planVillePane.getWidth());
			this.planVilleVue.setHeight(this.planVillePane.getHeight());
			this.planVilleVue.dessinerPlan(plan);
		}
	}

	public void afficherErreur(String erreur) {
		this.labelError.setStyle("-fx-text-fill : red;");
		this.labelError.setVisible(true);
		this.labelError.setText(erreur);
	}

	@FXML
	public void choixFichierAction(MouseEvent event) {
		FileChooser dialogue = new FileChooser();
		FileChooser.ExtensionFilter extensionsFilter = new FileChooser.ExtensionFilter("Fichier XML", "*.xml");
		dialogue.setSelectedExtensionFilter(extensionsFilter);
		File tmp = dialogue.showOpenDialog(this.controleur.getStage());
		if ((tmp != null) && tmp.getName().toLowerCase().endsWith(".xml")) {
			this.controleur.clicBoutonParcourir(true, tmp);
		} else {
			this.controleur.clicBoutonParcourir(false, null);
		}
	}

	@FXML
	public void validerAction(MouseEvent event) {
		if (this.controleur == null) {
			this.labelError.setVisible(true);
			this.labelError.setStyle("-fx-text-fill : red;");
			this.labelError.setText("Erreur : Controler is null, fatal error");
		} else if (this.fichierChoisie == null) {
			this.labelError.setVisible(true);
			this.labelError.setStyle("-fx-text-fill : red;");
			this.labelError.setText("Erreur : Aucun fichier choisi");
		} else {
			this.controleur.clicBoutonValider(this.fichierChoisie);
		}
	}

	public Controleur getControleur() {
		return this.controleur;
	}

	public void setControleur(Controleur controleur) {
		this.controleur = controleur;
	}

	public File getFichierChoisi() {
		return this.fichierChoisie;
	}

	public void fichierGlisserDeposerAccepteAction() {
		this.controleur.glisserDeposer(true, this.glisserDeposerFichierVue.getFichierChoisie());
	}

	public void fichierGlisserDeposerRefuseAction() {
		this.controleur.glisserDeposer(false, null);
	}

	public void fichierAccepte(File fichier) {
		this.labelError.setVisible(false);
		this.fichierChoisie = fichier;
		this.textFieldLienFichier.setText(this.fichierChoisie.getAbsolutePath());
		this.labelError.setVisible(true);
		this.labelError.setStyle("-fx-text-fill : green;");
		this.labelError.setText("Votre fichier a été pris en compte.");
		this.glisserDeposerFichierVue.getLabel().setText("Glisser-Déposer une autre demande de livraisons.");
	}

	public void fichierRefuse() {
		this.labelError.setVisible(false);
		this.fichierChoisie = null;
		this.textFieldLienFichier.setText("");
		this.labelError.setStyle("-fx-text-fill : red;");
		this.labelError.setVisible(true);
		this.labelError.setText("Erreur : Le fichier choisi est invalide.");
	}

	@FXML
	public void precedent() {
		this.controleur.clicBoutonRetour();
	}

	@FXML
	private void imageViewPrecedentEntered() {
		this.imageViewPrecedent.setImage(new Image(this.classLoader.getResource("precedent_bleu.png").toString()));
	}

	@FXML
	private void imageViewPrecedentExited() {
		this.imageViewPrecedent.setImage(new Image(this.classLoader.getResource("precedent_noir.png").toString()));
	}
}
