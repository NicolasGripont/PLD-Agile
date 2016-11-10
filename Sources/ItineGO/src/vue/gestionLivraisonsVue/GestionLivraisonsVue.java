package vue.gestionLivraisonsVue;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import controleur.Controleur;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import modeles.Livraison;
import modeles.Noeud;
import modeles.Plan;
import vue.gestionVue.GestionVue;
import vue.planVilleVue.PlanVilleVue;

/**
 * Vue affichant les solutions trouvées pour la tournée à effectuer en temps
 * réel
 */
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
	private Label labelHorraires;

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

	private Task<Void> taskCalcul;

	private final Tooltip tooltipAccueil = new Tooltip("Retour au choix du plan");

	private final Tooltip tooltipPrecedent = new Tooltip("Retour au choix de livraisons");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.boxStopperCalcule.setVisible(false);

		this.planVilleVue = new PlanVilleVue(this.planVillePane.getPrefWidth(), this.planVillePane.getPrefHeight(),
				this);
		this.planVillePane.getChildren().add(this.planVilleVue);

		final ChangeListener<Number> listener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				GestionLivraisonsVue.this.controleur.redessinerPlan();
			}

		};
		this.planVillePane.widthProperty().addListener(listener);
		this.planVillePane.heightProperty().addListener(listener);

		this.adresseColonne
				.setCellValueFactory(new Callback<CellDataFeatures<Livraison, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Livraison, String> param) {
						final Livraison livraison = param.getValue();
						return new SimpleStringProperty(String.valueOf(livraison.getNoeud().getId()));
					}
				});

		this.plageDebutColonne
				.setCellValueFactory(new Callback<CellDataFeatures<Livraison, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Livraison, String> param) {
						final Livraison livraison = param.getValue();
						if ((livraison.getDebutPlage() != null)
								&& !livraison.getDebutPlage().getHoraire().equals("00:00")) {
							return new SimpleStringProperty(livraison.getDebutPlage().getHoraire());
						} else {
							return new SimpleStringProperty("-");
						}
					}
				});

		this.plageFinColonne
				.setCellValueFactory(new Callback<CellDataFeatures<Livraison, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Livraison, String> param) {
						final Livraison livraison = param.getValue();
						if ((livraison.getFinPlage() != null)
								&& !livraison.getFinPlage().getHoraire().equals("00:00")) {
							return new SimpleStringProperty(livraison.getFinPlage().getHoraire());
						} else {
							return new SimpleStringProperty("-");
						}
					}
				});

		this.dureeColonne
				.setCellValueFactory(new Callback<CellDataFeatures<Livraison, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Livraison, String> param) {
						final Livraison livraison = param.getValue();
						return new SimpleStringProperty(String.valueOf(livraison.getDuree()));
					}
				});

		this.labelError.setVisible(false);

		this.imageViewAccueilExited();
		this.imageViewPrecedentExited();

		this.livraisonTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				Livraison livraison = (Livraison) newValue;
				GestionLivraisonsVue.this.planVilleVue.livraisonSelected(livraison);
			}
		});

		this.livraisonTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		Tooltip.install(this.imageViewPrecedent, this.tooltipPrecedent);
		Tooltip.install(this.imageViewAccueil, this.tooltipAccueil);
	}

	@Override
	public void selectionneNoeud(Noeud noeud) {
		for (Livraison l : this.livraisonTable.getItems()) {
			if (l.getNoeud().equals(noeud)) {
				this.livraisonTable.getSelectionModel().select(l);
			}
		}
	}

	public void miseAJourTableau(Plan plan) {
		if (plan != null) {
			if ((plan.getEntrepot() != null) && (plan.getEntrepot().getNoeud() != null)) {
				this.labelEntrepot
						.setText("Adresse de l'entrepôt : " + String.valueOf(plan.getEntrepot().getNoeud().getId()));
				this.labelHorraires.setText("Début Livraison à " + plan.getEntrepot().getHoraireDepart().getHoraire());
			}
			this.livraisonTable.getItems().clear();
			if (plan.getLivraisons() != null) {
				for (Map.Entry<Integer, Livraison> l : plan.getLivraisons().entrySet()) {
					if ((l != null) && (l.getKey() != null)) {
						this.livraisonTable.getItems().add(l.getValue());
					}
				}
			}
		}
	}

	public void dessinePlan(Plan plan) {
		if (plan != null) {
			this.planVilleVue.setWidth(this.planVillePane.getWidth());
			this.planVilleVue.setHeight(this.planVillePane.getHeight());
			this.planVilleVue.dessinerPlan(plan);
		}
	}

	public void afficherErreur(String erreur) {
		this.labelError.setVisible(true);
		this.labelError.setText(erreur);
	}

	public Controleur getControleur() {
		return this.controleur;
	}

	public void setControleur(Controleur controleur) {
		this.controleur = controleur;
	}

	@FXML
	public void home() {
		this.controleur.clicBoutonHome();
	}

	@FXML
	public void precedent() {
		this.controleur.clicBoutonRetour();
	}

	@FXML
	public void calculerLivraisonAction() {
		this.boutonCalculer.setVisible(false);
		this.boxStopperCalcule.setVisible(true);
		this.barreChargement.progressProperty().unbind();
		this.barreChargement.setProgress(0);
		// Thread calcul temps de calcule d'un tournee
		this.taskCalcul = new Task<Void>() {
			@Override
			protected Void call() {
				int tpsMax = GestionLivraisonsVue.this.controleur.getGestionnaire().getTempsMaxDeCalcul();
				int tps = 0;
				tpsMax /= 1000;
				for (tps = 0; tps <= tpsMax; tps++) {
					String tpsStr = "Temps restant : ";
					tpsStr += (tpsMax - tps) + "s";
					this.updateProgress(tps, tpsMax);
					if ((tps % 2) == 1) {
						tpsStr += "P";
					}
					this.updateMessage(tpsStr);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						return null;
					}
				}
				return null;
			}
		};
		this.taskCalcul.messageProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.endsWith("P")) {
					GestionLivraisonsVue.this
							.dessinePlan(GestionLivraisonsVue.this.controleur.getGestionnaire().getPlan());
					newValue = newValue.substring(0, newValue.length() - 1);
				}
				GestionLivraisonsVue.this.labelTempsRestant.setText(newValue);
			}
		});
		this.barreChargement.progressProperty().bind(this.taskCalcul.progressProperty());
		this.controleur.clicBoutonCalculerTournee();
		Thread th = new Thread(this.taskCalcul);
		th.setDaemon(true);
		th.start();
	}

	public void setHboxCalculLivraison(Boolean value) {
		this.boutonCalculer.setVisible(value);
		this.boxStopperCalcule.setVisible(value);
	}

	@FXML
	private void stopperCalculLivraisonAction() {
		this.setHboxCalculLivraison(false);
		if (this.taskCalcul != null) {
			this.taskCalcul.cancel();
			this.controleur.clicBoutonsStopperCalculeTournee();
		}
		this.taskCalcul = null;
	}

	@FXML
	private void imageViewPrecedentEntered() {
		this.imageViewPrecedent.setImage(new Image(this.classLoader.getResource("precedent_bleu.png").toString()));
	}

	@FXML
	private void imageViewPrecedentExited() {
		this.imageViewPrecedent.setImage(new Image(this.classLoader.getResource("precedent_noir.png").toString()));
	}

	@FXML
	private void imageViewAccueilEntered() {
		this.imageViewAccueil.setImage(new Image(this.classLoader.getResource("accueil_bleu.png").toString()));
	}

	@FXML
	private void imageViewAccueilExited() {
		this.imageViewAccueil.setImage(new Image(this.classLoader.getResource("accueil_noir.png").toString()));
	}
}
