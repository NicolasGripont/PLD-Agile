package vue.gestionTourneeVue;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controleur.Controleur;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import modeles.Horaire;
import modeles.Livraison;
import modeles.LivraisonTournee;
import modeles.Noeud;
import modeles.Plan;
import vue.gestionVue.GestionVue;
import vue.planVilleVue.PlanVilleVue;

/**
 * Vue affichant la tournée à effectuer après la fin du calcul
 */
public class GestionTourneeVue extends GestionVue {
	private Controleur controleur;

	private Noeud noeudSelectionne;
	private Noeud noeudLivraisonSelectionne;
	private boolean attenteNoeudPourNouvelleLivraison = false;
	private boolean attenteLivraisonPrecedentePourNouvelleLivraison = false;
	
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
	private TableColumn<LivraisonTournee, Boolean> supprimerColonne;

	@FXML
	private Label labelEntrepot;

	@FXML
	private Label labelHorraires;

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

	@FXML
	private ImageView imageViewModifier;

	@FXML
	private ImageView imageViewUndo;

	@FXML
	private ImageView imageViewRedo;

	@FXML
	private ImageView imageViewValiderModifications;

	@FXML
	private ImageView imageViewAnnulerModifications;

	@FXML
	private ImageView imageViewAjouterLivraison;

	@FXML
	private HBox hBoxBoutons;

	private final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		supprimerColonne.setVisible(false);
		// TODO Auto-generated method stub
		planVilleVue = new PlanVilleVue(planVillePane.getPrefWidth(), planVillePane.getPrefHeight(), this);
		planVillePane.getChildren().add(planVilleVue);

		final ChangeListener<Number> listener = new ChangeListener<Number>() {
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
			if (livraison.getLivraison().getDebutPlage() != null
					&& !livraison.getLivraison().getDebutPlage().getHoraire().equals("00:00")) {
				return new SimpleStringProperty(livraison.getLivraison().getDebutPlage().getHoraire());
			} else {
				return new SimpleStringProperty("-");
			}
		});

		plageFinColonne.setCellValueFactory(param -> {
			final LivraisonTournee livraison = param.getValue();
			if (livraison.getLivraison().getFinPlage() != null
					&& !livraison.getLivraison().getFinPlage().getHoraire().equals("00:00")) {
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

		supprimerColonne.setCellFactory(param -> {
			SupprimerLivraisonCell cell = new SupprimerLivraisonCell();
			cell.getImageViewMoins().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					int row = cell.getIndex();			
					controleur.clicBoutonSupprimer(row);
					System.out.println("Suppression ligne : " + row);
				}; 
			});
			return cell;
		});

		labelError.setVisible(false);
		labelInstruction.setVisible(false);

		imageViewAccueilExited();
		imageViewPrecedentExited();
		imageViewModifierExited();
		imageViewUndoExited();
		imageViewRedoExited();
		imageViewValiderModificationsExited();
		imageViewAnnulerModificationsExited();
		imageViewAjouterLivraisonExited();
		
		setVisibiliteBoutons(false);

		livraisonTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				LivraisonTournee livraison = (LivraisonTournee) newValue;
				planVilleVue.livraisonSelected(livraison.getLivraison());
			}
		});

		livraisonTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	public void solutionOptimale(boolean optimale) {
		if (optimale) {
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
		if(attenteNoeudPourNouvelleLivraison) {
			noeudNouvelleLivraisonSelectionne(noeud);
		} else if(attenteLivraisonPrecedentePourNouvelleLivraison) {
			livraisonPrecedenteSelectionne(noeud);
		} else {
			for (LivraisonTournee t : livraisonTable.getItems()) {
				if (t.getLivraison().getNoeud().equals(noeud)) {
					livraisonTable.getSelectionModel().select(t);
				}
			}
		}
	}

	public void miseAJourTableau(Plan plan, List<LivraisonTournee> list, Horaire horaireDebut, Horaire horaireFin) {
		labelEntrepot.setText("Adresse de l'entrepôt : " + String.valueOf(plan.getEntrepot().getNoeud().getId()));
		labelHorraires.setText("Début de la tournée : " + horaireDebut.getHoraire() + " - Fin de la tournée : "
				+ horaireFin.getHoraire());
		if (list != null && list.size() > 0) {
			for (LivraisonTournee l : list) {
				livraisonTable.getItems().add(l);
			}
		}
	}

	public void dessinePlan(Plan plan) {
		if (plan != null) {
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
	public void precedent() {
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
	private void imageViewAccueilExited() {
		imageViewAccueil.setImage(new Image(classLoader.getResource("accueil_noir.png").toString()));
	}

	@FXML
	private void imageViewModifierEntered() {
		imageViewModifier.setImage(new Image(classLoader.getResource("modifier_bleu.png").toString()));
	}

	@FXML
	private void imageViewModifierExited() {
		imageViewModifier.setImage(new Image(classLoader.getResource("modifier_noir.png").toString()));
	}

	@FXML
	private void imageViewUndoEntered() {
		imageViewUndo.setImage(new Image(classLoader.getResource("undo_bleu.png").toString()));
	}

	@FXML
	private void imageViewUndoExited() {
		imageViewUndo.setImage(new Image(classLoader.getResource("undo_noir.png").toString()));
	}

	@FXML
	private void imageViewRedoEntered() {
		imageViewRedo.setImage(new Image(classLoader.getResource("redo_bleu.png").toString()));
	}

	@FXML
	private void imageViewRedoExited() {
		imageViewRedo.setImage(new Image(classLoader.getResource("redo_noir.png").toString()));
	}

	@FXML
	private void imageViewValiderModificationsEntered() {
		imageViewValiderModifications.setImage(new Image(classLoader.getResource("valider_bleu.png").toString()));
	}

	@FXML
	private void imageViewValiderModificationsExited() {
		imageViewValiderModifications.setImage(new Image(classLoader.getResource("valider_noir.png").toString()));
	}

	@FXML
	private void imageViewAnnulerModificationsEntered() {
		imageViewAnnulerModifications.setImage(new Image(classLoader.getResource("annuler_bleu.png").toString()));
	}

	@FXML
	private void imageViewAnnulerModificationsExited() {
		imageViewAnnulerModifications.setImage(new Image(classLoader.getResource("annuler_noir.png").toString()));
	}

	@FXML
	private void imageViewAjouterLivraisonEntered() {
		imageViewAjouterLivraison.setImage(new Image(classLoader.getResource("plus2_bleu.png").toString()));
	}

	@FXML
	private void imageViewAjouterLivraisonExited() {
		imageViewAjouterLivraison.setImage(new Image(classLoader.getResource("plus2_noir.png").toString()));
	}

	@FXML
	private void imageViewModifierClicked() {
		//TODO appeler controleur{
		setVisibiliteBoutons(true);
		supprimerColonne.setVisible(true);
		System.out.println("imageViewModifierClicked");
	}


	@FXML
	private void imageViewUndoClicked() {
		//TODO appeler controleur
		System.out.println("imageViewUndoClicked");
	}

	@FXML
	private void imageViewRedoClicked() {
		//TODO appeler controleur
		System.out.println("imageViewRedoClicked");
	}

	@FXML
	private void imageViewValiderModificationsClicked() {
		//TODO appeler controleur
		labelInstruction.setVisible(false);
		setVisibiliteBoutons(false);
		supprimerColonne.setVisible(false);
		System.out.println("imageViewValiderModificationsClicked");
	}

	@FXML
	private void imageViewAnnulerModificationsClicked() {
		//TODO appeler controleur
		labelInstruction.setVisible(false);
		setVisibiliteBoutons(false);
		supprimerColonne.setVisible(false);
		System.out.println("imageViewAnnulerModificationsClicked");
	}

	@FXML
	private void imageViewAjouterLivraisonClicked() {
		System.out.println("imageViewAjouterLivraison");
		labelInstruction.setVisible(true);
		labelInstruction.setText("Sélectionnez un noeud sur le plan");
		planVilleVue.modeAjouterLivraison(true);
		attenteNoeudPourNouvelleLivraison = true;
	}
	
	private void noeudNouvelleLivraisonSelectionne(Noeud noeud) {
		attenteNoeudPourNouvelleLivraison = false;
		attenteLivraisonPrecedentePourNouvelleLivraison = true;
		noeudSelectionne = noeud;
		labelInstruction.setText("Sélectionnez la livraison précedent la nouvelle livraison");
	}
	
	private void livraisonPrecedenteSelectionne(Noeud noeud) {
		for (LivraisonTournee t : livraisonTable.getItems()) {
			if (t.getLivraison().getNoeud().equals(noeud)) {
				noeudLivraisonSelectionne = noeud;
				ajouterLivraison();
				return;
			}
		}
	}
	
	private void ajouterLivraison() {
		if(noeudLivraisonSelectionne != null && noeudSelectionne != null) {
			attenteLivraisonPrecedentePourNouvelleLivraison = false;
			planVilleVue.modeAjouterLivraison(false);
			labelInstruction.setText("Vous pouvez maintenant modifer la durée et les plages horaires");
			//Dégeulasse à changer, il faut passer par le controleur et le modèle
			livraisonTable.getItems().add(new LivraisonTournee(new Livraison(noeudSelectionne, 0), new Horaire("0:0:0"), new Horaire("0:0:0")));
		}
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

	public void supprimerLivraison(int indexRow) {
		controleur.clicBoutonSupprimer(indexRow);
		System.err.println("Supprimer la ligne " + indexRow);
		
	}

	public void setVisibiliteBoutons(Boolean modeModification) {
		hBoxBoutons.getChildren().clear();		
		if(modeModification) {
			hBoxBoutons.getChildren().add(imageViewValiderModifications);
			hBoxBoutons.getChildren().add(imageViewAnnulerModifications);
			hBoxBoutons.getChildren().add(imageViewAjouterLivraison);
		} else {
			hBoxBoutons.getChildren().add(imageViewAccueil);
			hBoxBoutons.getChildren().add(imageViewPrecedent);
			hBoxBoutons.getChildren().add(imageViewModifier);
			hBoxBoutons.getChildren().add(imageViewUndo);
			hBoxBoutons.getChildren().add(imageViewRedo);
		}
		hBoxBoutons.getChildren().add(labelError);
	}
}