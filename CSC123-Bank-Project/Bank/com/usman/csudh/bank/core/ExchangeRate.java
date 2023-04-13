package com.usman.csudh.bank.core;
public class ExchangeRate {
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
