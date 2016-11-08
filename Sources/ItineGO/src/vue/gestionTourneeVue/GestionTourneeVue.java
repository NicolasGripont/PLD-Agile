package vue.gestionTourneeVue;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import controleur.Controleur;
import controleur.EtatAjouterTourneeDuree;
import controleur.EtatAjouterTourneeOrdre;
import controleur.EtatAjouterTourneePlace;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import modeles.Horaire;
import modeles.Livraison;
import modeles.Noeud;
import modeles.Plan;
import vue.gestionVue.GestionVue;
import vue.planVilleVue.PlanVilleVue;
import javafx.util.Callback;

/**
 * Vue affichant la tournée à effectuer après la fin du calcul
 */
public class GestionTourneeVue extends GestionVue {
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
	private TableColumn<Livraison, String> dureeColonne;

	@FXML
	private TableColumn<Livraison, Boolean> supprimerColonne;

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
	
	@FXML
	private Button boutonGenerer;

	private final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
			final Livraison livraison = param.getValue();
			if(livraison.getNoeud().getId() != -1) {
				return new SimpleStringProperty(String.valueOf(livraison.getNoeud().getId()));
			} else { 
				return new SimpleStringProperty("?");
			}
		});

		plageDebutColonne.setCellValueFactory(param -> {
			final Livraison livraison = param.getValue();
			if (livraison.getDebutPlage() != null
					&& !livraison.getDebutPlage().getHoraire().equals("00:00")) {
				return new SimpleStringProperty(livraison.getDebutPlage().getHoraire());
			} else {
				return new SimpleStringProperty("-");
			}
		});

		plageFinColonne.setCellValueFactory(param -> {
			final Livraison livraison = param.getValue();
			if (livraison.getFinPlage() != null
					&& !livraison.getFinPlage().getHoraire().equals("00:00")) {
				return new SimpleStringProperty(livraison.getFinPlage().getHoraire());
			} else {
				return new SimpleStringProperty("-");
			}
		});

		arriveeColonne.setCellValueFactory(param -> {
			final Livraison livraison = param.getValue();
			if(livraison.getHeureArrive() == null) {
				return new SimpleStringProperty("-");
			} else {
				return new SimpleStringProperty(livraison.getHeureArrive().getHoraire());
			}
		});

		departColonne.setCellValueFactory(param -> {
			final Livraison livraison = param.getValue();
			if(livraison.getHeureDepart() == null) {
				return new SimpleStringProperty("-");
			} else {
				return new SimpleStringProperty(livraison.getHeureDepart().getHoraire());
			}
		});

		dureeColonne.setCellValueFactory(param -> {
			final Livraison livraison = param.getValue();
			
			if(livraison.getNoeud().equals(controleur.getGestionnaire().getPlan().getEntrepot().getNoeud())) {
				return new SimpleStringProperty("-");
			}
			return new SimpleStringProperty(String.valueOf(livraison.getDuree()));
		});

		supprimerColonne.setCellFactory(new Callback<TableColumn<Livraison, Boolean>, TableCell<Livraison, Boolean>>() {
		      @Override public TableCell<Livraison, Boolean> call(TableColumn<Livraison, Boolean> livraisonBooleanTableColumn) {
		    	  SupprimerLivraisonCell cell = new SupprimerLivraisonCell(livraisonTable,controleur.getGestionnaire().getPlan().getEntrepot());
					cell.getImageViewMoins().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
						public void handle(MouseEvent event) {
							int row = cell.getIndex();			
							controleur.clicBoutonSupprimer(row);
							System.out.println("Suppression ligne : " + row);
						}; 
					});
					return cell;
		      }
		    });
		
		imageViewAccueilExited();
		imageViewPrecedentExited();
		imageViewModifierExited();
		imageViewUndoExited();
		imageViewRedoExited();
		imageViewValiderModificationsExited();
		imageViewAnnulerModificationsExited();
		imageViewAjouterLivraisonExited();
		
		livraisonTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				Livraison livraison = (Livraison) newValue;
				planVilleVue.livraisonSelected(livraison);
			}
		});

		livraisonTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		planVilleVue.modeAjouterLivraison(false);
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
		if(controleur.getEtatCourant().getClass().equals(EtatAjouterTourneePlace.class)) {
			controleur.clicPlanNoeud(noeud);			
		} else if(controleur.getEtatCourant().getClass().equals(EtatAjouterTourneeOrdre.class)) {
			controleur.clicPlanLivraison(noeud);	
		} else {
			for (Livraison t : livraisonTable.getItems()) {
				if (t.getNoeud().equals(noeud)) {
					livraisonTable.getSelectionModel().select(t);
				}
			}
		}
	}

	public void miseAJourTableau(Plan plan, List<Livraison> list, Horaire horaireDebut, Horaire horaireFin) {
		livraisonTable.getItems().clear();
		labelEntrepot.setText("Adresse de l'entrepôt : " + String.valueOf(plan.getEntrepot().getNoeud().getId()));
		labelHorraires.setText("Début de la tournée : " + horaireDebut.getHoraire() + " - Fin de la tournée : "
				+ horaireFin.getHoraire());
		if (list != null && list.size() > 0) {
			for (Livraison l : list) {
				livraisonTable.getItems().add(l);
			}
			Livraison l = new Livraison(plan.getEntrepot().getNoeud(), 0, "0:0:0", "0:0:0");
			l.setHeureDepart(horaireFin);
			l.setHeureArrive(list.get(list.size()-1).getHeureDepart());
			livraisonTable.getItems().add(l);
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
		controleur.clicBoutonModifier();
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
		controleur.clicBoutonSauvegarder();
	}

	@FXML
	private void imageViewAnnulerModificationsClicked() {
		controleur.clicBoutonAnnuler();
	}

	@FXML
	private void imageViewAjouterLivraisonClicked() {
		controleur.clicBoutonAjouter();
	}
	
	public void majEtatModifierTournee() {
		hBoxBoutons.getChildren().clear();
		hBoxBoutons.getChildren().add(imageViewValiderModifications);
		hBoxBoutons.getChildren().add(imageViewAnnulerModifications);
		hBoxBoutons.getChildren().add(imageViewAjouterLivraison);
		hBoxBoutons.getChildren().add(labelError);
		boutonGenerer.setVisible(false);
		supprimerColonne.setVisible(true);
		labelInstruction.setVisible(false);
	}
	
	public void majAjouterTourneePlace() {
		hBoxBoutons.getChildren().clear();
		hBoxBoutons.getChildren().add(imageViewAnnulerModifications);
		hBoxBoutons.getChildren().add(labelError);
		labelInstruction.setVisible(true);
		labelInstruction.setText("Sélectionnez un noeud sur le plan");
		planVilleVue.modeAjouterLivraison(true);
		Livraison l = new Livraison(new Noeud(-1,-1,-1), 0,"0:0:0", "0:0:0");
		l.setHeureArrive(new Horaire("0:0:0"));
		l.setHeureDepart(new Horaire("0:0:0"));
		livraisonTable.getItems().add(l);
		livraisonTable.getSelectionModel().select(livraisonTable.getItems().size()-1);
		supprimerColonne.setVisible(false);
		planVilleVue.modeAjouterLivraison(true);
	}
	
	public void majAjouterTourneeOrdre(Livraison livraison) {
		livraisonTable.getItems().set(livraisonTable.getItems().size()-1, livraison);
		livraisonTable.refresh();
		livraisonTable.getSelectionModel().select(livraison);
		labelInstruction.setText("Sélectionnez la livraison qui suit la nouvelle livraison");
	}
	
	public void majAjouterTourneeDuree() {
		planVilleVue.modeAjouterLivraison(false);
		labelInstruction.setText("Vous pouvez maintenant modifer la durée");
		dureeColonne.setCellFactory(TextFieldTableCell.forTableColumn());
		dureeColonne.setCellFactory(new Callback<TableColumn<Livraison, String>, TableCell<Livraison, String>>() {
			@Override
			public TableCell<Livraison, String> call(TableColumn<Livraison, String> livraisonBooleanTableColumn) {
				EditionCell cell = new EditionCell(livraisonTable);
				return cell;
			}
	    });
		
		livraisonTable.setEditable(true);
		livraisonTable.getSelectionModel().setCellSelectionEnabled(true);	
		livraisonTable.getFocusModel().focus(new TablePosition<>(livraisonTable, livraisonTable.getItems().size()-1, dureeColonne));
		livraisonTable.edit(livraisonTable.getFocusModel().getFocusedCell().getRow(), dureeColonne);
		//livraisonTable.getSelectionModel().select(livraisonTable.getItems().size()-1);
		/*dureeColonne.setOnEditStart(value -> {
			    new EventHandler<CellEditEvent<Livraison, String>>() {
			        @Override
			        public void handle(CellEditEvent<Livraison, String> t) {
			            ((Livraison) value.getSource());
			        }
			    };
		});*/
		dureeColonne.setOnEditCommit( value -> {
			    new EventHandler<CellEditEvent<Livraison, String>>() {
			        @Override
			        public void handle(CellEditEvent<Livraison, String> t) {
			        	int duree = 0;
			        	try {
			        		duree = Integer.valueOf(t.getNewValue());
				            ((Livraison) livraisonTable.getItems().get(t.getTablePosition().getRow()) ).setDuree(duree);
				            controleur.entrerDuree(duree);
				            planVilleVue.modeAjouterLivraison(false);
				    		dureeColonne.setOnEditCommit(null);
			        	} catch(Exception e) {
			        		labelError.setText("Durée : Données invalide");
			        	}
			        }
			    };
		});
		
		//livraisonTable.isCellEditable(row, col)
	}
	
	public void majVisualiserTournee() {
		hBoxBoutons.getChildren().clear();
		hBoxBoutons.getChildren().add(imageViewAccueil);
		hBoxBoutons.getChildren().add(imageViewPrecedent);
		hBoxBoutons.getChildren().add(imageViewModifier);
		hBoxBoutons.getChildren().add(imageViewUndo);
		hBoxBoutons.getChildren().add(imageViewRedo);
		hBoxBoutons.getChildren().add(labelError);
		boutonGenerer.setVisible(true);
		dureeColonne.setOnEditCommit(null);
		supprimerColonne.setVisible(false);
		labelError.setVisible(false);
		labelInstruction.setVisible(false);
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