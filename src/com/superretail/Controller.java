package com.superretail;

import com.superretail.models.ItemData;
import com.superretail.models.PackedItem;
import com.superretail.models.StockItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

public class Controller {

    @FXML
    public BorderPane dashBoard;
    @FXML
    public ListView<com.superretail.models.StockItem> itemListView;
    @FXML
    public TextArea detailsArea;

    public void initialize() {
        itemListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        itemListView.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<StockItem>() {
            @Override
            public void changed(ObservableValue<? extends StockItem> observableValue,
                                StockItem stockItem, StockItem newItem) {
                if(newItem != null) {
                    StockItem newStockItem = itemListView.getSelectionModel().getSelectedItem();
                    detailsArea.setText(newStockItem.toString());
                }
            }
        });

        SortedList<StockItem> sortedList = new SortedList<>(
                ItemData.getInstance().getStockItemList(),
                new Comparator<StockItem>() {
                    @Override
                    public int compare(StockItem stockItem, StockItem item) {
                        return stockItem.getItemName().compareToIgnoreCase(item.getItemName());
                    }
                }
        );

        itemListView.setItems(sortedList);
        itemListView.getSelectionModel().selectFirst();

        itemListView.setCellFactory(new Callback<ListView<StockItem>, ListCell<StockItem>>() {
            @Override
            public ListCell<StockItem> call(ListView<StockItem> stockItemListView) {
                ListCell<StockItem> cell = new ListCell<StockItem>() {
                    @Override
                    protected void updateItem(StockItem stockItem, boolean b) {
                        super.updateItem(stockItem, b);
                        if(b) {
                            setText(null);
                        } else {
                            setText(stockItem.getItemName());
                        }
                    }
                };
                return cell;
            }
        });
   }

   @FXML
    public void onClickAdd() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(dashBoard.getScene().getWindow());
        dialog.setTitle("Add new Stock item");
        dialog.setHeaderText("Create a new Stock item here");
        dialog.setHeight(600);
        dialog.setWidth(500);
        dialog.setResizable(true);
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
        itemListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void deleteItem() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Stock Item");
        alert.setHeaderText(itemListView.getSelectionModel().getSelectedItem().getItemName());
        alert.setContentText(
                itemListView.getSelectionModel().getSelectedItem().toString()
        );
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            detailsArea.setText(null);
            ItemData.getInstance().getStockItemList().remove(itemListView.getSelectionModel().getSelectedItem());
        }
        itemListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void updateItem() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(dashBoard.getScene().getWindow());
        dialog.setTitle("Update Stock item");
        dialog.setHeaderText("UpdateStock item here");
        dialog.setHeight(600);
        dialog.setWidth(500);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("update-item-dialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e) {
            System.out.println("Couldn't load dialog");
            e.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        UpdateItemDialogController controller = fxmlLoader.getController();
        StockItem itemToUpdate = itemListView.getSelectionModel().getSelectedItem();
        controller.initData(itemToUpdate);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            controller.processResult();
        }
        itemListView.setItems(ItemData.getInstance().getStockItemList());
        itemListView.getSelectionModel().select(itemToUpdate);
    }
}
