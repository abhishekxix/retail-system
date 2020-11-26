package com.superretail;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    public BorderPane dashBoard;

    public void initialize() {

   }

    public void onClickAdd() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(dashBoard.getScene().getWindow());
        dialog.setTitle("Add new Stock item");
        dialog.setHeaderText("Create a new Stock item here");
        dialog.setHeight(600);
        dialog.setWidth(500);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("add-item-dialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e) {
            System.out.println("Couldn't load dialog");
            e.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            AddItemDialogController controller = fxmlLoader.getController();
            controller.processResult();
        }
    }
}
