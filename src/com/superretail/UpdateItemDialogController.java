package com.superretail;

import com.superretail.models.PackedItem;
import com.superretail.models.StockItem;
import com.superretail.models.UnpackedItem;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.w3c.dom.Text;

public class UpdateItemDialogController {

    private StockItem selectedItem;

    @FXML
    public GridPane gridPane;
    @FXML
    public Label itemCode;
    @FXML
    public TextField itemName;
    @FXML
    public TextField itemPrice;

    private Label quantityLabel = new Label();
    private TextField quantityField = new TextField();
    private Label mfgDateLabel = new Label("Manufacture Date:");
    private Label mfgDate = new Label();
    private Label expDateLabel = new Label("Expiration Date:");
    private Label expDate = new Label();
    private Alert alert = new Alert(Alert.AlertType.NONE);

    public void initialize() {

    }

    /**
     * This method accepts a StockItem to initialize the view
     * @param stockItem
     **/
    public void initData(StockItem stockItem) {
        selectedItem = stockItem;
        itemCode.setText(selectedItem.getCode());
        itemName.setText(selectedItem.getItemName());
        itemPrice.setText(String.valueOf(selectedItem.getPrice()));

        if(selectedItem instanceof UnpackedItem) {
            if(((UnpackedItem) selectedItem).isFluid()) {
                quantityLabel.setText("Quantity(Ltr.):");
            } else {
                quantityLabel.setText("Quantity(Kg.):");
            }
            quantityField.setText(String.valueOf(((UnpackedItem) selectedItem).getQuantity()));
            gridPane.add(quantityLabel, 0, 3);
            gridPane.add(quantityField, 1, 3);

        } else if(selectedItem instanceof PackedItem){
            quantityLabel.setText("Quantity");
            quantityField.setText(String.valueOf(((PackedItem) selectedItem).getQuantity()));
            gridPane.add(quantityLabel, 0, 3);
            gridPane.add(quantityField, 1, 3);
            gridPane.add(mfgDateLabel, 0, 4);
            mfgDate.setText(((PackedItem) selectedItem).getManufactureDate().toString());
            gridPane.add(mfgDate, 1, 4);
            gridPane.add(expDateLabel, 0, 5);
            expDate.setText(((PackedItem) selectedItem).getExpirationDate().toString());
            gridPane.add(expDate, 1, 5);
        }
    }

    public void processResult() {
        if(itemName.getText().trim().equals("")) {
            showItemNotUpdatedAlert("Invalid Name");
            return;
        }
        selectedItem.setItemName(itemName.getText());

        try {
            Double price = Double.parseDouble(itemPrice.getText());
            selectedItem.setPrice(price);

        } catch(NumberFormatException e) {
            showItemNotUpdatedAlert("Invalid Price");
        }

        if(selectedItem instanceof UnpackedItem) {
            try {
                Double quantity = Double.parseDouble(quantityField.getText());
                ((UnpackedItem) selectedItem).setQuantity(quantity);

            } catch(NumberFormatException e) {
                showItemNotUpdatedAlert("Invalid Quantity");
            }

        } else if(selectedItem instanceof PackedItem) {
            try {
                Integer quantity = Integer.parseInt(quantityField.getText());
                ((PackedItem)selectedItem).setQuantity(quantity);

            } catch(NumberFormatException e) {
                showItemNotUpdatedAlert("Invalid Quantity");
            }
        }
    }

    private void showItemNotUpdatedAlert(String reason) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setHeaderText("Error updating Item.");
        alert.setContentText(reason);
        alert.showAndWait();
    }
}
