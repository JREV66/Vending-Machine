package com.techelevator;

import java.math.BigDecimal;

public class Candy extends ProductInVendingMachine {


    public Candy(String slotID, String name, BigDecimal price, String category, int quantity){

        super (slotID, name, price, category, quantity,  "Yummy Yummy, So Sweet! ");
    }


}
