package com.github.jtam2000.stockquotes;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table
/*
    This class purposely the exact copy of all functions of  StockQuote, to demo that exact difference
    between Hibernate using XML configuration file vs JPA Annotation
 */
public class StockQuoteWithAnnotation {

    private String ticker;
    private float bid;
    private float ask;
    private String currency;
    private double available_shares;

    public List<LocalDate> getDividend_date() {
        return dividend_date;
    }

    public void setDividend_date(List<LocalDate> dividend_date) {
        this.dividend_date = dividend_date;
    }

    @ElementCollection
    private List<LocalDate> dividend_date;

    @Transient
    private String valueNotPersistedToDb;
    private int changedFields=0;

    public int getChangedFields() {
        return changedFields;
    }

    public void setChangedFields(int changedFields) {
        this.changedFields = changedFields;
    }

    private double outstanding_shares;

    @Id
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

    @Override
    public String toString() {

        return "StockQuoteWithAnnotation = {" +
                "quote_timestamp=" + quote_timestamp +
                ",ticker='" + ticker + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", currency='" + currency + '\'' +
                ", available_shares=" + available_shares +
                ", outstanding_shares=" + outstanding_shares +
                ", dividend_date=" + dividend_date +
                "}";
    }

    public String getValueNotPersistedToDb() {
        return valueNotPersistedToDb;
    }

    public void setValueNotPersistedToDb(String valueNotPersistedToDb) {
        this.valueNotPersistedToDb = valueNotPersistedToDb;
    }

}
