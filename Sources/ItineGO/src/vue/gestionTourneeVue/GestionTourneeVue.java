package vue.gestionTourneeVue;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controleur.Controleur;
import controleur.EtatAjouterTourneeOrdre;
import controleur.EtatAjouterTourneePlace;
import controleur.EtatModifierTournee;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import modeles.Horaire;
import modeles.Livraison;
import modeles.Noeud;
import modeles.Plan;
import vue.gestionVue.GestionVue;
import vue.planVilleVue.PlanVilleVue;

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
	private TableColumn<Livraison, String> attenteColonne;

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

	private final Tooltip tooltipAccueil = new Tooltip("Retour au choix du plan");

	private final Tooltip tooltipPrecedent = new Tooltip("Retour à la visualisation des livraisons");

	private final Tooltip tooltipModifier = new Tooltip("Modifier la tournée");

	private final Tooltip tooltipUndo = new Tooltip("Annuler la dernière modification");

	private final Tooltip tooltipRedo = new Tooltip("Retablir la dernière modification");

	private final Tooltip tooltipValiderModifications = new Tooltip("Valider les modifications");

	private final Tooltip tooltipAnnulerModifications = new Tooltip("Annuler les modifications");

	private final Tooltip tooltipAjouterLivraison = new Tooltip("Ajouter une livraison");

	private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

	private Callback<TableColumn<Livraison, String>, TableCell<Livraison, String>> defaultStringCellFactory;

	/**
	 * Méthode IHM - Itinialisation de la vue
	 * 
	 * @param location
	 *            : URL de la vue
	 *            
	 * @param resources
	 *            : ressources        
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.planVilleVue = new PlanVilleVue(this.planVillePane.getPrefWidth(), this.planVillePane.getPrefHeight(),
				this);
		this.planVillePane.getChildren().add(this.planVilleVue);

		final ChangeListener<Number> listener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				GestionTourneeVue.this.controleur.redessinerPlan();
			}

		};

		this.planVillePane.widthProperty().addListener(listener);
		this.planVillePane.heightProperty().addListener(listener);
		this.livraisonTable.setRowFactory(new Callback<TableView<Livraison>, TableRow<Livraison>>() {
			@Override
			public TableRow<Livraison> call(TableView<Livraison> tv) {
				TableRow<Livraison> row = new TableRow<>();
				row.setOnDragDetected(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (!row.isEmpty() && GestionTourneeVue.this.controleur.getEtatCourant().getClass()
								.equals(EtatModifierTournee.class)) {
							Integer index = row.getIndex();
							Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
							db.setDragView(row.snapshot(null, null));
							ClipboardContent cc = new ClipboardContent();
							cc.put(SERIALIZED_MIME_TYPE, index);
							db.setContent(cc);
							event.consume();
						}
					}
				});

				row.setOnDragOver(new EventHandler<DragEvent>() {
					@Override
					public void handle(DragEvent event) {
						Dragboard db = event.getDragboard();
						if (db.hasContent(SERIALIZED_MIME_TYPE) && GestionTourneeVue.this.controleur.getEtatCourant()
								.getClass().equals(EtatModifierTournee.class)) {
							if (row.getIndex() != ((Integer) db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
								event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
								event.consume();
								int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
								if ((draggedIndex != row.getIndex())
										&& (row.getIndex() < GestionTourneeVue.this.livraisonTable.getItems().size())) {
									row.setStyle("-fx-cell-size: 55px;-fx-padding: 30 0 0 0;");
								}
							}
						}
					}
				});

				row.setOnDragExited(new EventHandler<DragEvent>() {
					@Override
					public void handle(DragEvent event) {
						Dragboard db = event.getDragboard();
						if (db.hasContent(SERIALIZED_MIME_TYPE) && GestionTourneeVue.this.controleur.getEtatCourant()
								.getClass().equals(EtatModifierTournee.class)) {
							int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
							if (!row.isEmpty() && (draggedIndex != row.getIndex())) {
								row.setStyle("-fx-padding: 0em;");
							}
						}
					}
				});

				row.setOnDragDropped(new EventHandler<DragEvent>() {
					@Override
					public void handle(DragEvent event) {
						Dragboard db = event.getDragboard();
						if (db.hasContent(SERIALIZED_MIME_TYPE) && GestionTourneeVue.this.controleur.getEtatCourant()
								.getClass().equals(EtatModifierTournee.class)) {
							int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
							int dropIndex;
							if (row.isEmpty()) {
								dropIndex = GestionTourneeVue.this.livraisonTable.getItems().size();
							} else {
								dropIndex = row.getIndex();
							}
							if ((dropIndex != draggedIndex)
									&& (dropIndex < GestionTourneeVue.this.livraisonTable.getItems().size())) {
								System.out
										.println("On echange la ligne " + draggedIndex + " avec la ligne " + dropIndex);
								GestionTourneeVue.this.controleur.modifierOrdre(draggedIndex, dropIndex);
							}
						}
					}
				});
				return row;
			}
		});

		this.adresseColonne
				.setCellValueFactory(new Callback<CellDataFeatures<Livraison, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Livraison, String> param) {
						final Livraison livraison = param.getValue();
						if (livraison.getNoeud().getId() != -1) {
							return new SimpleStringProperty(String.valueOf(livraison.getNoeud().getId()));
						} else {
							return new SimpleStringProperty("?");
						}
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

		this.arriveeColonne
				.setCellValueFactory(new Callback<CellDataFeatures<Livraison, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Livraison, String> param) {
						final Livraison livraison = param.getValue();
						if (livraison.getHeureArrive() == null) {
							return new SimpleStringProperty("-");
						} else {
							// System.err.println("HA -"+
							// livraison.getHeureArrive().getHoraire());
							return new SimpleStringProperty(livraison.getHeureArrive().getHoraire());
						}
					}
				});

		this.departColonne
				.setCellValueFactory(new Callback<CellDataFeatures<Livraison, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Livraison, String> param) {
						final Livraison livraison = param.getValue();
						if (livraison.getHeureDepart() == null) {
							return new SimpleStringProperty("-");
						} else {
							return new SimpleStringProperty(livraison.getHeureDepart().getHoraire());
						}
					}
				});

		this.dureeColonne
				.setCellValueFactory(new Callback<CellDataFeatures<Livraison, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Livraison, String> param) {
						final Livraison livraison = param.getValue();

						if (livraison.getNoeud().equals(GestionTourneeVue.this.controleur.getGestionnaire().getPlan()
								.getEntrepot().getNoeud())) {
							return new SimpleStringProperty("-");
						}
						return new SimpleStringProperty(String.valueOf(livraison.getDuree()));
					}
				});

		this.attenteColonne
				.setCellValueFactory(new Callback<CellDataFeatures<Livraison, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Livraison, String> param) {
						final Livraison livraison = param.getValue();
						if (livraison.getTempsAttente() == 0) {
							return new SimpleStringProperty(String.valueOf("-"));
						} else {
							return new SimpleStringProperty(String.valueOf(livraison.getTempsAttente()));
						}
					}
				});

		this.supprimerColonne
				.setCellFactory(new Callback<TableColumn<Livraison, Boolean>, TableCell<Livraison, Boolean>>() {
					@Override
					public TableCell<Livraison, Boolean> call(
							TableColumn<Livraison, Boolean> livraisonBooleanTableColumn) {
						SupprimerLivraisonCell cell = new SupprimerLivraisonCell(GestionTourneeVue.this.livraisonTable,
								GestionTourneeVue.this.controleur.getGestionnaire().getPlan().getEntrepot());
						cell.getImageViewMoins().addEventFilter(MouseEvent.MOUSE_CLICKED,
								new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent event) {
										int row = cell.getIndex();
										GestionTourneeVue.this.controleur.clicBoutonSupprimer(row);
									};
								});
						return cell;
					}
				});

		this.imageViewAccueilExited();
		this.imageViewPrecedentExited();
		this.imageViewModifierExited();
		this.imageViewUndoExited();
		this.imageViewRedoExited();
		this.imageViewValiderModificationsExited();
		this.imageViewAnnulerModificationsExited();
		this.imageViewAjouterLivraisonExited();

		this.livraisonTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				Livraison livraison = (Livraison) newValue;
				GestionTourneeVue.this.planVilleVue.livraisonSelected(livraison);
			}
		});

		this.livraisonTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.planVilleVue.modeAjouterLivraison(false);

		Tooltip.install(this.imageViewPrecedent, this.tooltipPrecedent);
		Tooltip.install(this.imageViewAccueil, this.tooltipAccueil);
		Tooltip.install(this.imageViewModifier, this.tooltipModifier);
		Tooltip.install(this.imageViewUndo, this.tooltipUndo);
		Tooltip.install(this.imageViewRedo, this.tooltipRedo);
		Tooltip.install(this.imageViewValiderModifications, this.tooltipValiderModifications);
		Tooltip.install(this.imageViewAnnulerModifications, this.tooltipAnnulerModifications);
		Tooltip.install(this.imageViewAjouterLivraison, this.tooltipAjouterLivraison);
		this.livraisonTable.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {

				if (e.getCode() == KeyCode.TAB) {
					GestionTourneeVue.this.livraisonTable.getSelectionModel().selectNext();
					e.consume();
					return;
				} else if (e.getCode() == KeyCode.ENTER) { 
					GestionTourneeVue.this.livraisonTable.getSelectionModel().selectBelowCell();
					e.consume();
					return;
				}
			}
		});

		this.defaultStringCellFactory = this.plageDebutColonne.getCellFactory();
	}

	/**
	 * Affiche dans le label si la solution est optimale
	 * 
	 * @param optimale
	 *            : boolean pour optimale
	 *                    
	 */
	public void solutionOptimale(boolean optimale) {
		if (optimale) {
			this.labelError.setVisible(true);
			this.labelError.setStyle("-fx-text-fill : green;");
			this.labelError.setText("La solution est optimale");
		} else {
			this.labelError.setVisible(true);
			this.labelError.setStyle("-fx-text-fill : blue;");
			this.labelError.setText("La solution n'est pas optimale");
		}
	}

	/**
	 * Méthode appellée par PlanVille quand un noeud est selectionné dans le plan
	 * 
	 * @param noeud
	 *            : le noeud selectionné
	 */
	@Override
	public void selectionneNoeud(Noeud noeud) {
		if (this.controleur.getEtatCourant().getClass().equals(EtatAjouterTourneePlace.class)) {
			this.controleur.clicPlanNoeud(noeud);
		} else if (this.controleur.getEtatCourant().getClass().equals(EtatAjouterTourneeOrdre.class)) {
			for (Livraison t : this.livraisonTable.getItems()) {
				if (t.getNoeud().equals(noeud)) {
					this.controleur.clicPlanLivraison(noeud, this.livraisonTable.getItems().lastIndexOf(t));
				}
			}
		} else {
			for (Livraison t : this.livraisonTable.getItems()) {
				if (t.getNoeud().equals(noeud)) {
					this.livraisonTable.getSelectionModel().select(t);
				}
			}
		}
	}

	/**
	 * Méthode qui met à jour le tableau contenant les livraisons
	 * 
	 * @param plan
	 *            : plan qui contient les livraisons
	 */
	public void miseAJourTableau(Plan plan, List<Livraison> list, Horaire horaireDebut, Horaire horaireFin) {
		this.livraisonTable.getItems().clear();
		this.labelEntrepot.setText("Adresse de l'entrepôt : " + String.valueOf(plan.getEntrepot().getNoeud().getId()));
		this.labelHorraires.setText("Début de la tournée : " + horaireDebut.getHoraire() + " - Fin de la tournée : "
				+ horaireFin.getHoraire());
		if ((list != null) && (list.size() > 0)) {
			for (Livraison l : list) {
				this.livraisonTable.getItems().add(l);
			}
			Livraison l = new Livraison(plan.getEntrepot().getNoeud(), 0, "0:0:0", "0:0:0");
			l.setHeureDepart(null);
			l.setHeureArrive(horaireFin);
			this.livraisonTable.getItems().add(l);
		}
	}

	/**
	 * Permet de redessiner le plan dans la vue
	 * 
	 * @param plan
	 *            : Plan a dessiner
	 */
	public void dessinePlan(Plan plan) {
		if (plan != null) {
			this.planVilleVue.setWidth(this.planVillePane.getWidth());
			this.planVilleVue.setHeight(this.planVillePane.getHeight());
			this.planVilleVue.dessinerPlan(plan);
		}
	}

	/**
	 * Affiche une erreur dans le label error de la vue
	 * 
	 * @param erreur
	 *            : Erreur à afficher
	 */
	public void afficherErreur(String erreur) {
		this.labelError.setStyle("-fx-text-fill : red;");
		this.labelError.setVisible(true);
		this.labelError.setText(erreur);
	}

	/**
	 * Affiche une info dans le label de la vue
	 * 
	 * @param message
	 *      : message à afficher
	 */
	public void afficherInfo(String message) {
		this.labelError.setStyle("-fx-text-fill : blue;");
		this.labelError.setVisible(true);
		this.labelError.setText(message);
	}

	/**
	 * Affiche une info dans le label de la vue
	 * 
	 * @param message
	 *      : message à afficher  
	 */
	public void afficherInfoSucces(String message) {
		this.labelError.setStyle("-fx-text-fill : green;");
		this.labelError.setVisible(true);
		this.labelError.setText(message);
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

	@FXML
	private void imageViewModifierEntered() {
		this.imageViewModifier.setImage(new Image(this.classLoader.getResource("modifier_bleu.png").toString()));
	}

	@FXML
	private void imageViewModifierExited() {
		this.imageViewModifier.setImage(new Image(this.classLoader.getResource("modifier_noir.png").toString()));
	}

	@FXML
	private void imageViewUndoEntered() {
		this.imageViewUndo.setImage(new Image(this.classLoader.getResource("undo_bleu.png").toString()));
	}

	@FXML
	private void imageViewUndoExited() {
		this.imageViewUndo.setImage(new Image(this.classLoader.getResource("undo_noir.png").toString()));
	}

	@FXML
	private void imageViewRedoEntered() {
		this.imageViewRedo.setImage(new Image(this.classLoader.getResource("redo_bleu.png").toString()));
	}

	@FXML
	private void imageViewRedoExited() {
		this.imageViewRedo.setImage(new Image(this.classLoader.getResource("redo_noir.png").toString()));
	}

	@FXML
	private void imageViewValiderModificationsEntered() {
		this.imageViewValiderModifications
				.setImage(new Image(this.classLoader.getResource("valider_bleu.png").toString()));
	}

	@FXML
	private void imageViewValiderModificationsExited() {
		this.imageViewValiderModifications
				.setImage(new Image(this.classLoader.getResource("valider_noir.png").toString()));
	}

	@FXML
	private void imageViewAnnulerModificationsEntered() {
		this.imageViewAnnulerModifications
				.setImage(new Image(this.classLoader.getResource("annuler_bleu.png").toString()));
	}

	@FXML
	private void imageViewAnnulerModificationsExited() {
		this.imageViewAnnulerModifications
				.setImage(new Image(this.classLoader.getResource("annuler_noir.png").toString()));
	}

	@FXML
	private void imageViewAjouterLivraisonEntered() {
		this.imageViewAjouterLivraison.setImage(new Image(this.classLoader.getResource("plus2_bleu.png").toString()));
	}

	@FXML
	private void imageViewAjouterLivraisonExited() {
		this.imageViewAjouterLivraison.setImage(new Image(this.classLoader.getResource("plus2_noir.png").toString()));
	}

	@FXML
	private void imageViewModifierClicked() {
		this.controleur.clicBoutonModifier();
	}

	@FXML
	private void imageViewUndoClicked() {
		this.controleur.undo();
	}

	@FXML
	private void imageViewRedoClicked() {
		this.controleur.redo();
	}

	@FXML
	private void imageViewValiderModificationsClicked() {
		this.controleur.clicBoutonSauvegarder();
	}

	@FXML
	private void imageViewAnnulerModificationsClicked() {
		this.controleur.clicBoutonAnnuler();
	}

	@FXML
	private void imageViewAjouterLivraisonClicked() {
		this.controleur.clicBoutonAjouter();
	}

	/**
	 * Permet de redessiner l'ihm quand on est dans l'etat modifier tournee
	 * 
	 */
	public void majEtatModifierTournee() {
		this.hBoxBoutons.getChildren().clear();
		this.hBoxBoutons.getChildren().add(this.imageViewValiderModifications);
		this.hBoxBoutons.getChildren().add(this.imageViewAnnulerModifications);
		this.hBoxBoutons.getChildren().add(this.imageViewAjouterLivraison);
		this.hBoxBoutons.getChildren().add(this.labelError);
		this.boutonGenerer.setVisible(false);
		this.supprimerColonne.setVisible(true);
		this.labelInstruction.setVisible(false);
		Callback callback = new Callback<TableColumn<Livraison, String>, TableCell<Livraison, String>>() {
			@Override
			public TableCell<Livraison, String> call(TableColumn<Livraison, String> livraisonStringTableColumn) {
				EditionCell cell = new EditionCell();
				return cell;
			}
		};

		this.plageDebutColonne.setCellFactory(callback);
		this.plageDebutColonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Livraison, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Livraison, String> t) {
				if (!t.getOldValue().equals(t.getNewValue())) {
					GestionTourneeVue.this.controleur.modifierPlageDebut(t.getTablePosition().getRow(),
							t.getNewValue() + ":00");
				}
			}
		});

		this.plageFinColonne.setCellFactory(callback);
		this.plageFinColonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Livraison, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Livraison, String> t) {
				if (!t.getOldValue().equals(t.getNewValue())) {
					GestionTourneeVue.this.controleur.modifierPlageFin(t.getTablePosition().getRow(),
							t.getNewValue() + ":00");
				}
			}
		});

		this.livraisonTable.setEditable(true);
	}

	/**
	 * Permet de redessiner l'ihm quand on est dans l'etat ajouter tournee
	 * 
	 */
	public void majAjouterTourneePlace() {
		this.hBoxBoutons.getChildren().clear();
		this.hBoxBoutons.getChildren().add(this.imageViewAnnulerModifications);
		this.hBoxBoutons.getChildren().add(this.labelError);
		this.labelInstruction.setVisible(true);
		this.labelInstruction.setText("Sélectionnez un noeud sur le plan");
		this.planVilleVue.modeAjouterLivraison(true);
		Livraison l = new Livraison(new Noeud(-1, -1, -1), 0, "0:0:0", "0:0:0");
		l.setHeureArrive(new Horaire("0:0:0"));
		l.setHeureDepart(new Horaire("0:0:0"));
		this.livraisonTable.getItems().add(l);
		this.livraisonTable.getSelectionModel().select(this.livraisonTable.getItems().size() - 1);
		this.supprimerColonne.setVisible(false);
		this.planVilleVue.modeAjouterLivraison(true);
		// labelError.setText("Ajout d'une livraison");
		this.plageDebutColonne.setCellFactory(this.defaultStringCellFactory);
		this.plageFinColonne.setCellFactory(this.defaultStringCellFactory);
	}

	/**
	 * Permet de redessiner l'ihm quand on est dans l'etat tournee ordre
	 * 
	 */
	public void majAjouterTourneeOrdre(Livraison livraison) {
		this.livraisonTable.getItems().set(this.livraisonTable.getItems().size() - 1, livraison);
		this.livraisonTable.refresh();
		this.livraisonTable.getSelectionModel().select(livraison);
		this.labelInstruction.setText("Sélectionnez la livraison qui suit la nouvelle livraison");
	}

	/**
	 * Permet de redessiner l'ihm quand on est dans l'etat tournee duree
	 * 
	 */
	public void majAjouterTourneeDuree() {
		this.planVilleVue.modeAjouterLivraison(false);
		this.labelInstruction.setText("Vous pouvez maintenant modifer la durée");
		this.livraisonTable.setEditable(true);
		this.livraisonTable.getSelectionModel().setCellSelectionEnabled(true);
		this.dureeColonne.setCellFactory(new Callback<TableColumn<Livraison, String>, TableCell<Livraison, String>>() {
			@Override
			public TableCell<Livraison, String> call(TableColumn<Livraison, String> livraisonBooleanTableColumn) {
				EditionCell cell = new EditionCell();
				return cell;
			}
		});
		this.dureeColonne.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Livraison, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Livraison, String> t) {
				int duree = 0;
				try {
					duree = Integer.valueOf(t.getNewValue());
					// ((Livraison)
					// livraisonTable.getItems().get(t.getTablePosition().getRow())).setDuree(duree);
					GestionTourneeVue.this.controleur.entrerDuree(duree);
					GestionTourneeVue.this.planVilleVue.modeAjouterLivraison(false);
					GestionTourneeVue.this.dureeColonne.setOnEditCommit(null);
				} catch (Exception e) {
					GestionTourneeVue.this.afficherErreur("Durée : Donnée invalide");
					e.printStackTrace();
					GestionTourneeVue.this.labelError.setText("Durée : Donnée invalide");
					GestionTourneeVue.this.labelError.setStyle("-fx-text-fill : red;");
				}
			}
		});
		// Permet de synchronize l'IHM et le Thread RunLater
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				GestionTourneeVue.this.livraisonTable.getFocusModel()
						.focus(new TablePosition<>(GestionTourneeVue.this.livraisonTable,
								GestionTourneeVue.this.livraisonTable.getItems().size() - 1,
								GestionTourneeVue.this.dureeColonne));
				GestionTourneeVue.this.livraisonTable.edit(
						GestionTourneeVue.this.livraisonTable.getFocusModel().getFocusedCell().getRow(),
						GestionTourneeVue.this.dureeColonne);
			}
		});
	}

	/**
	 * Permet de redessiner l'ihm quand on est dans l'etat visualiser tournee
	 * 
	 */
	public void majVisualiserTournee() {
		this.hBoxBoutons.getChildren().clear();
		this.hBoxBoutons.getChildren().add(this.imageViewAccueil);
		this.hBoxBoutons.getChildren().add(this.imageViewPrecedent);
		this.hBoxBoutons.getChildren().add(this.imageViewModifier);
		this.hBoxBoutons.getChildren().add(this.imageViewUndo);
		this.hBoxBoutons.getChildren().add(this.imageViewRedo);
		this.hBoxBoutons.getChildren().add(this.labelError);
		this.boutonGenerer.setVisible(true);
		this.dureeColonne.setOnEditCommit(null);
		this.supprimerColonne.setVisible(false);
		// labelError.setVisible(true);
		this.labelInstruction.setVisible(false);
		this.livraisonTable.getSelectionModel().setCellSelectionEnabled(false);
	}

	/**
	 * méthode appele quand on genere la feuille de route
	 * 
	 */
	@FXML
	private void genererFeuilleDeRoute() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Générer feuille de route");
		File file = fileChooser.showSaveDialog(this.controleur.getStage());
		if (file != null) {
			this.controleur.clicBoutonGenererFeuilleDeRoute(file.getAbsolutePath());
		}
	}

	public void desactiverUndo(Boolean value) {
		this.imageViewUndo.setDisable(value);
		if (value) {
			this.imageViewUndo.setImage(new Image(this.classLoader.getResource("undo_gris.png").toString()));
		} else {
			this.imageViewUndo.setImage(new Image(this.classLoader.getResource("undo_noir.png").toString()));
		}
	}

	public void desactiverRedo(Boolean value) {
		this.imageViewRedo.setDisable(value);
		if (value) {
			this.imageViewRedo.setImage(new Image(this.classLoader.getResource("redo_gris.png").toString()));
		} else {
			this.imageViewRedo.setImage(new Image(this.classLoader.getResource("redo_noir.png").toString()));
		}
	}

}