package com.techelevator;

import java.math.BigDecimal;

public class  Transaction {

    private BigDecimal moneyInput = BigDecimal.valueOf(0.00);
    private final BigDecimal ONE_DOLLAR = BigDecimal.valueOf(1.00);

    private final BigDecimal FIVE_DOLLAR = BigDecimal.valueOf(5.00);

    private final BigDecimal TEN_DOLLAR = BigDecimal.valueOf(10.00);
    private final BigDecimal TWENTY_DOLLAR = BigDecimal.valueOf(20.00);
    private  BigDecimal balance = BigDecimal.valueOf(0.00);
    private int itemQuantity = 5;




    public BigDecimal getMoneyInput() {
        return moneyInput;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void addBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void minusBalance(BigDecimal amount){
        this.balance = this.balance.subtract(amount);
    }

}


