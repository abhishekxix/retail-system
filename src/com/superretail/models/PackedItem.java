package com.superretail.models;

import java.util.Date;

public class PackedItem extends StockItem {

    /**
     * The quantity here determines the number of packages of an item.
     **/
    private Integer quantity;

    public PackedItem(String code, String itemName, Double price, Integer quantity, String brand,
                    Date expirationDate, Date manufactureDate) {
        super.code = code;
        super.itemName = itemName;
        super.price = price;
        super.brand = brand;
        super.expirationDate = expirationDate;
        super.manufactureDate = manufactureDate;
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
