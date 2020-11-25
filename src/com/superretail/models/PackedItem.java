package com.superretail.models;

import java.time.LocalDate;
import java.util.Date;

public class PackedItem extends StockItem {

    /**
     * The quantity here determines the number of packages of an item.
     **/
    private Integer quantity;
    private LocalDate manufactureDate;
    private LocalDate expirationDate;
    private String brand;

    public PackedItem(String code, String itemName, Double price, Integer quantity, String brand,
                      LocalDate manufactureDate, LocalDate expirationDate) {
        super.code = code;
        super.itemName = itemName;
        super.price = price;
        this.brand = brand;
        this.expirationDate = expirationDate;
        this.manufactureDate = manufactureDate;
        this.quantity = quantity;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format(
                "Code: %s\n" +
                        "Brand: %s\n" +
                        "Name: %s\n" +
                        "Price: %f\n" +
                        "Quantity: %d\n" +
                        "Manufacture Date: %s\n" +
                        "Expiration Date: %s\n",
                code,
                brand,
                itemName,
                price,
                quantity,
                manufactureDate,
                expirationDate
        );
    }
}
