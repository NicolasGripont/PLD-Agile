package vue.gestionTourneeVue;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import modeles.LivraisonTournee;

public class SupprimerLivraisonCell extends TableCell<LivraisonTournee, LivraisonTournee> {

	final ImageView imageViewMoins = new ImageView();

	// pads and centers the imageViewMoins in the cell.
	final StackPane paddedButton = new StackPane();

	private final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

	private LivraisonTournee livraisonTournee;

	public SupprimerLivraisonCell() {
		paddedButton.setPadding(new Insets(3));
		paddedButton.getChildren().add(imageViewMoins);

		imageViewMoins.setFitHeight(20);
		imageViewMoins.setFitWidth(20);

		imageViewMoins.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				System.out.println("*supprimer*");
			}
		});

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

		imageViewMoinsExited();
	}

	@Override
	protected void updateItem(LivraisonTournee item, boolean empty) {
		super.updateItem(item, empty);
		if (!empty) {
			setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			setGraphic(paddedButton);
			System.out.println(item);
		} else {
			setGraphic(null);
		}
	}

	private void imageViewMoinsEntered() {
		imageViewMoins.setImage(new Image(classLoader.getResource("moins_bleu.png").toString()));
	}

	private void imageViewMoinsExited() {
		imageViewMoins.setImage(new Image(classLoader.getResource("moins_noir.png").toString()));
	}
}