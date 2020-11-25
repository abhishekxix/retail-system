package com.superretail.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;



public class ItemData {

    private  final DateTimeFormatter formatter;
    private ObservableList<StockItem> stockItemList;
    private static final ItemData instance = new ItemData();
    private static final String fileName = "stock-items.dat";

    private ItemData() {
        stockItemList = FXCollections.observableArrayList();
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public void load() {
        Path path = Paths.get(fileName);

        try(ObjectInputStream saveFile =
                    new ObjectInputStream(new BufferedInputStream(Files.newInputStream(path)))) {

            boolean eof = false;
            while(!eof) {
                try {
                    StockItem item = (StockItem) saveFile.readObject();
                    stockItemList.add(item);
                } catch (IOException e) {
                    eof = true;
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();

        }
    }

    public void save() {
        Path path = Paths.get(fileName);

        try(ObjectOutputStream saveFile =
                    new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(path)))) {

            for(var item : stockItemList) {
                saveFile.writeObject(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addItem(StockItem item) {
        stockItemList.add(item);
    }

    public static ItemData getInstance() {
        return instance;
    }

    public ObservableList<StockItem> getStockItemList() {
        return stockItemList;
    }

}
