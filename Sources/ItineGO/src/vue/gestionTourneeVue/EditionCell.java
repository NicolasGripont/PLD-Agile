package vue.gestionTourneeVue;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modeles.Livraison;

public class EditionCell extends TableCell<Livraison, String> {

	private TextField textField;

	public EditionCell() {
	}

	@Override
	public void startEdit() {
		if (!this.isEmpty()) {
			super.startEdit();
			this.createTextField();
			this.setText(null);
			this.setGraphic(this.textField);
			this.textField.requestFocus();
			this.textField.selectAll();
		}
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		this.setText(this.getItem());
		this.setGraphic(null);
	}

	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			this.setText(null);
			this.setGraphic(null);
		} else {
			if (this.isEditing()) {
				if (this.textField != null) {
					this.textField.setText(this.getString());
				}
				this.setText(null);
				this.setGraphic(this.textField);
			} else {
				this.setText(this.getString());
				this.setGraphic(null);
			}
		}
	}

	private void createTextField() {

		this.textField = new TextField(this.getString());
		this.textField.setMinWidth(this.getWidth() - (this.getGraphicTextGap() * 2));

		this.textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue = true && (newValue == false)) {
					EditionCell.this.commitEdit(EditionCell.this.textField.getText());
				}
			}
		});

		this.textField.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.ESCAPE) {
					EditionCell.this.cancelEdit();
				} else if (e.getCode() == KeyCode.ENTER) {
					EditionCell.this.commitEdit(EditionCell.this.textField.getText());
				}
			}
		});
	}

	private String getString() {
		return this.getItem() == null ? "" : this.getItem().toString();
	}

}