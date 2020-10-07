package com.github.jtam2000.stockquotes;

import com.github.jtam2000.jpa.HasPrimaryKey;

import java.time.LocalDateTime;

public class StockQuote {

    private String ticker;
    private float bid;
    private float ask;
    private String currency;
    private double available_shares;

    @Override
    public String toString() {
        return "StockQuote{" +
                "ticker='" + ticker + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", currency='" + currency + '\'' +
                ", available_shares=" + available_shares +
                ", outstanding_shares=" + outstanding_shares +
                ", quote_timestamp=" + quote_timestamp +
                '}';
    }

    private double outstanding_shares;

    private LocalDateTime quote_timestamp;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public float getBid() {
        return bid;
    }

    public void setBid(float bid) {
        this.bid = bid;
    }

    public float getAsk() {
        return ask;
    }

    public void setAsk(float ask) {
        this.ask = ask;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAvailable_shares() {
        return available_shares;
    }

    public void setAvailable_shares(double available_shares) {
        this.available_shares = available_shares;
    }

    public double getOutstanding_shares() {
        return outstanding_shares;
    }

    public void setOutstanding_shares(double outstanding_shares) {
        this.outstanding_shares = outstanding_shares;
    }

    public LocalDateTime getQuote_timestamp() {
        return quote_timestamp;
    }

    public void setQuote_timestamp(LocalDateTime quote_timestamp) {
        this.quote_timestamp = quote_timestamp;
    }

}
