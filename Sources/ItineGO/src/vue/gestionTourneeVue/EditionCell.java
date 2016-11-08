package vue.gestionTourneeVue;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modeles.Livraison;

class EditionCell extends TableCell<Livraison, String> {

    private TextField textField;

    public EditionCell() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.requestFocus();
            textField.selectAll();
        } 
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText((String) getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {

        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        // commit on focus lost
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {

            if( oldValue = true && newValue == false) {

                System.out.println( "Focus lost, current value: " + textField.getText());

                commitEdit();

            }
        });

        // cancel edit on ESC
        textField.addEventFilter(KeyEvent.KEY_RELEASED, e -> {

            if( e.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }

        });

    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

    private boolean commitEdit() {
        super.commitEdit(textField.getText());
        return true; // TODO: add verifier and check if commit was possible
    }
}
