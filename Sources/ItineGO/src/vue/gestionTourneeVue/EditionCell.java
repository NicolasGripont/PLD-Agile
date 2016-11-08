package vue.gestionTourneeVue;

import javafx.beans.value.ChangeListener;import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modeles.Livraison;

public class EditionCell  extends TableCell<Livraison, String>{
	private final TextField textField = new TextField();
	private final Label label = new Label();
	private TableView<Livraison> table;
	
	EditionCell(TableView<Livraison> table) { 
		this.table = table;
        textProperty().bind(itemProperty());
        setGraphic(textField);
        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue.booleanValue()) {
                	textField.setText("Toto");
                	KeyEvent press = new KeyEvent(textField, textField, KeyEvent.KEY_PRESSED, "", "", KeyCode.BACK_SPACE, false, false, false, false);
                	textField.fireEvent(press);
                	//textField.requestFocus();
                }  
            }
        });
    }
	
	@Override
	protected void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (!empty) {
			setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			if(getIndex() == (table.getItems().size()-1)) {
				setGraphic(textField);
				textField.setText(item);
			}
			else {
				setGraphic(label);
				label.setText(item);
			}
				
		} else {
			setGraphic(null);
		}
	}
	
	
}
