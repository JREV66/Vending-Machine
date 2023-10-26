package com.techelevator;

import java.math.BigDecimal;

public class Drink extends ProductInVendingMachine {


    public Drink(String slotID, String name, BigDecimal price, String category, int quantity){

        super (slotID, name, price, category, quantity,"Glug Glug, Yum! ");

    }


}
