package com.caleb.cryptocurrencyconverter;

/**
 * Created by caleb on 10/12/17.
 */

public class ExchangeRate {
    private String mText;
    private double mExRateValue;

    public ExchangeRate(String text, double exRateValue) {
        this.mText = text;
        this.mExRateValue = exRateValue;
    }

    public String getText() {
        return mText;
    }

    public double getExRateValue() {
        return mExRateValue;
    }

}
