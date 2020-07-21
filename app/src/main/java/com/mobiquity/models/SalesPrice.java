package com.mobiquity.models;

import java.io.Serializable;

public class SalesPrice implements Serializable {

    private double amount;
    private String currency;

    public SalesPrice(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public SalesPrice(){

    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
