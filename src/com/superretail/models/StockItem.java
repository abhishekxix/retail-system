package com.superretail.models;

import java.util.Date;

abstract class StockItem {
    String code;
    String itemName;
    Double price;
    Date manufactureDate;
    Date expirationDate;
    String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

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

    public Date getManufactureDate() {
        return manufactureDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

}