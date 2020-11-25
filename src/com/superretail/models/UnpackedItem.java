package com.superretail.models;

import java.time.LocalDate;
import java.util.Date;

public class UnpackedItem extends StockItem {

    /**
     * The quantity attribute determines the weight of the item in kilograms if it is not fluid and
     * volume in litres otherwise.
     **/
    private Double quantity;
    private final boolean isFluid;


    public UnpackedItem(String code, String itemName, Double price, Double quantity, boolean isFluid) {
        super.code = code;
        super.itemName = itemName;
        super.price = price;
        this.quantity = quantity;
        this.isFluid = isFluid;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public boolean isFluid() {
        return isFluid;
    }
}
