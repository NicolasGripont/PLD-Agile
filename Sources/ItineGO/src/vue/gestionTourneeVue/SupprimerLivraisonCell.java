package vue.gestionTourneeVue;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import modeles.Entrepot;
import modeles.Livraison;

public class SupprimerLivraisonCell extends TableCell<Livraison, Boolean> {

	final ImageView imageViewMoins = new ImageView();

	// pads and centers the imageViewMoins in the cell.
	final StackPane paddedButton = new StackPane();

	private final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

	private final TableView<Livraison> table;

	private final Entrepot entrepot;

	public SupprimerLivraisonCell(TableView<Livraison> table, Entrepot entrepot) {
		this.table = table;
		this.entrepot = entrepot;
		this.paddedButton.setPadding(new Insets(3));
		this.paddedButton.getChildren().add(this.imageViewMoins);

		this.imageViewMoins.setFitHeight(20);
		this.imageViewMoins.setFitWidth(20);

		this.imageViewMoins.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				SupprimerLivraisonCell.this.imageViewMoinsEntered();
			}
		});

		this.imageViewMoins.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				SupprimerLivraisonCell.this.imageViewMoinsExited();
			}
		});
		Tooltip tooltipMoins = new Tooltip("Supprimer la livraison");
		Tooltip.install(this.imageViewMoins, tooltipMoins);
		this.imageViewMoinsExited();
	}

	public ImageView getImageViewMoins() {
		return this.imageViewMoins;
	}

	@Override
	protected void updateItem(Boolean item, boolean empty) {
		super.updateItem(item, empty);
		if (!empty
				&& ((this.table.getItems().get(this.getIndex()).getNoeud().getId() != this.entrepot.getNoeud().getId()))
				&& (this.table.getItems().size() > 2)) {
			this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			this.setGraphic(this.paddedButton);
		} else {
			this.setGraphic(null);
		}
	}

	private void imageViewMoinsEntered() {
		this.imageViewMoins.setImage(new Image(this.classLoader.getResource("moins2_bleu.png").toString()));
	}

	private void imageViewMoinsExited() {
		this.imageViewMoins.setImage(new Image(this.classLoader.getResource("moins2_noir.png").toString()));
	}
}