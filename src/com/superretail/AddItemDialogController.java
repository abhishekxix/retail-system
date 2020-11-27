package com.superretail;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddItemDialogController {

    @FXML
    public RadioButton tbPacked;
    @FXML
    public RadioButton tbUnpacked;
    @FXML
    public DialogPane dialogPane;
    @FXML
    public GridPane gridPane;
    @FXML
    private TextField itemPrice;
    @FXML
    private TextField itemName;
    @FXML
    private TextField itemCode;
    @FXML
    private ToggleGroup itemToggleGroup;

    private List<Node> unpackedNodesList = new ArrayList<>();
    private List<Node> packedNodesList = new ArrayList<>();
    private Label quantityLabel = new Label();
    private TextField quantityField = new TextField();
    private Label mfgDateLabel = new Label("Manufacture Date:");
    private Label expDateLabel = new Label("Expiration Date:");
    private DatePicker mfgDatePicker = new DatePicker(LocalDate.now());
    private DatePicker expDatePicker = new DatePicker();
    private Label brandLabel = new Label("Brand:");
    private TextField brandField = new TextField();
    private boolean isFluid;
    private Alert alert = new Alert(Alert.AlertType.NONE);


    public void initialize() {
        itemToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle,
                                Toggle t1) {
                if(t1 == tbUnpacked) {
                    hidePackedProperties();
                    addFluiditySelector();
                    showUnpackedProperties();

                } else {
                    hideUnpackedProperties();
                    showPackedProperties();
                }
            }
        });
    }

    private void addFluiditySelector() {
        ToggleGroup fluidity = new ToggleGroup();
        RadioButton fluid = new RadioButton("Fluid");
        RadioButton notFluid = new RadioButton("Not Fluid");
        fluid.setToggleGroup(fluidity);
        notFluid.setToggleGroup(fluidity);

        Label fluidityLabel = new Label("Type of item:");
        gridPane.add(fluidityLabel, 0, 6);
        gridPane.add(fluid, 1, 6);
        gridPane.add(notFluid, 2, 6);
        addIfAbsent(unpackedNodesList, fluidityLabel, fluid, notFluid);

        fluidity.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {

                if(t1 == fluid) {
                    quantityLabel.setText("Quantity(Ltr.):");
                    isFluid = true;
                } else {
                    quantityLabel.setText("Quantity(Kg.):");
                    isFluid = false;
                }
            }
        });
        fluidity.selectToggle(fluid);
    }


    private void showPackedProperties() {
        quantityLabel.setText("Quantity: ");
        gridPane.add(quantityLabel, 0, 5);
        gridPane.add(quantityField, 1, 5);
        gridPane.add(mfgDateLabel, 0, 6);
        gridPane.add(mfgDatePicker, 1, 6);
        gridPane.add(expDateLabel, 0, 7);
        gridPane.add(expDatePicker, 1, 7);
        gridPane.add(brandLabel, 0, 8);
        gridPane.add(brandField, 1, 8);
        addIfAbsent(packedNodesList, quantityLabel, quantityField, mfgDateLabel, mfgDatePicker,
                expDateLabel, expDatePicker, brandLabel, brandField);
    }

    private void showUnpackedProperties() {
        gridPane.add(quantityLabel, 0, 7);
        gridPane.add(quantityField, 1, 7);
        addIfAbsent(unpackedNodesList, quantityLabel, quantityField);
    }

    private void hideUnpackedProperties() {
        for(var node : unpackedNodesList) {
            gridPane.getChildren().remove(node);
        }
    }

    private void hidePackedProperties() {
        for(var node : packedNodesList) {
            gridPane.getChildren().remove(node);
        }
    }

    private void addIfAbsent(List<Node> list, Node... nodes) {
       for(var node : nodes) {
            if(!list.contains(node)) {
                list.add(node);
            }
       }
    }

    public void processResult() {
        if(itemCode.getText().trim().equals("") || itemName.getText().trim().equals("")) {
            showItemNotSavedAlert("Invalid Name or Code.");
            return;
        }

        if(itemToggleGroup.getSelectedToggle() == tbUnpacked) {

            try{
                com.superretail.models.ItemData.getInstance().addItem(new com.superretail.models.UnpackedItem(
                        itemCode.getText(),
                        itemName.getText(),
                        Double.parseDouble(itemPrice.getText()),
                        Double.parseDouble(quantityField.getText()),
                        isFluid
                ));
            } catch(NumberFormatException e) {
                showItemNotSavedAlert("Invalid Price or Quantity.");
            }
        } else {

            try{
                if(brandField.getText().trim().equals("")) {
                    brandField.setText("Generic");
                }
                com.superretail.models.ItemData.getInstance().addItem(new com.superretail.models.PackedItem(
                        itemCode.getText(),
                        itemName.getText(),
                        Double.parseDouble(itemPrice.getText()),
                        Integer.parseInt(quantityField.getText()),
                        brandField.getText(),
                        mfgDatePicker.getValue(),
                        expDatePicker.getValue()
                ));
            } catch(NumberFormatException e) {
                showItemNotSavedAlert("Invalid Price or Quantity.");
            } catch(Exception e) {
                showItemNotSavedAlert("Something went wrong, Please try again.");
            }
        }
    }

    private void showItemNotSavedAlert(String reason) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setHeaderText("Error adding Item.");
        alert.setContentText(reason);
        alert.showAndWait();
    }
}
