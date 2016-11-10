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
	
	private TableView<Livraison> table;
	
	private Entrepot entrepot;
	
	public SupprimerLivraisonCell(TableView<Livraison> table, Entrepot entrepot) {
		this.table = table;
		this.entrepot = entrepot;
		paddedButton.setPadding(new Insets(3));
		paddedButton.getChildren().add(imageViewMoins);

		imageViewMoins.setFitHeight(20);
		imageViewMoins.setFitWidth(20);
		
		

		imageViewMoins.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				imageViewMoinsEntered();
			}
		});

		imageViewMoins.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				imageViewMoinsExited();
			}
		});
		Tooltip tooltipMoins = new Tooltip("Supprimer la livraison");
		Tooltip.install(imageViewMoins, tooltipMoins);
		imageViewMoinsExited();
	}
	
	public ImageView getImageViewMoins() {
		return imageViewMoins;
	}

	@Override
	protected void updateItem(Boolean item, boolean empty) {
		super.updateItem(item, empty);
		if (!empty && (( table.getItems().get(this.getIndex()).getNoeud().getId()!= entrepot.getNoeud().getId())) && table.getItems().size() > 3) {
			setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			setGraphic(paddedButton);
		} else {
			setGraphic(null);
		}
	}

	private void imageViewMoinsEntered() {
		imageViewMoins.setImage(new Image(classLoader.getResource("moins2_bleu.png").toString()));
	}

	private void imageViewMoinsExited() {
		imageViewMoins.setImage(new Image(classLoader.getResource("moins2_noir.png").toString()));
	}
}