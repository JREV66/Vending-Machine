package com.techelevator;

import java.math.BigDecimal;

public abstract class ProductInVendingMachine {
    // assertions
    private String slotID = "";
    private String name = "";
    private BigDecimal price;
    private String category = "";
    private int quantity = 5;
    private String message = "";


    // constructor

    public ProductInVendingMachine(String slotID, String name, BigDecimal price, String category, int quantity) {
        this.slotID = slotID;
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;

    }

    public ProductInVendingMachine(String slotID, String name, BigDecimal price, String category, int quantity, String message) {
        this.slotID = slotID;
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        this.message = message;
    }



    public String getMessage() {
        return name + "! " +  message;
    }

    // getters
    public String getSlotID() {
        return slotID;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategory() {
        return this.category;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void  lowerQuantity() {
        quantity -= 1;
    }

}
