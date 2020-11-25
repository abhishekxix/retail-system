package com.superretail.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

abstract class StockItem implements Serializable {
    String code;
    String itemName;
    Double price;

    private static final long serialversionuid = 1l;


    public String getCode() {
        return code;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}