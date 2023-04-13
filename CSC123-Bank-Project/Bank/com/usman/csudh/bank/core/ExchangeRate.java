package com.usman.csudh.bank.core;

import java.io.Serializable;

public class ExchangeRate implements Serializable{
    private String name;
    private double rate; 

    public ExchangeRate(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }
}
