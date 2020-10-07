package com.github.jtam2000.stockquotes;

import com.github.jtam2000.jpa.HasPrimaryKey;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.hibernate.annotations.CascadeType.REMOVE;

@Entity
@Table
/*
    This class purposely the exact copy of all functions of  StockQuote, to demo that exact difference
    between Hibernate using XML configuration file vs JPA Annotation
 */
public class StockQuoteWithAnnotation implements HasPrimaryKey {

    private String ticker;
    private float bid;
    private float ask;
    private String currency;
    private double available_shares;

    public List<LocalDate> getDividend_date() {
        return dividend_date;
    }

    public void setDividend_date(List<LocalDate> dividend_date) {

        this.dividend_date = List.copyOf(dividend_date);
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "quote_timestamp")
    @OnDelete(action = OnDeleteAction.CASCADE)
    //LEARNING: the remove is crucial, it does proper cascade deletion
    @Cascade(value = REMOVE)
    private List<LocalDate> dividend_date;

    @Transient
    private String valueNotPersistedToDb;
    private int changedFields = 0;

    public int getChangedFields() {
        return changedFields;
    }

    public void setChangedFields(int changedFields) {
        this.changedFields = changedFields;
    }

    private double outstanding_shares;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockQuoteWithAnnotation that = (StockQuoteWithAnnotation) o;
        return Float.compare(that.bid, bid) == 0 &&
                Float.compare(that.ask, ask) == 0 &&
                Double.compare(that.available_shares, available_shares) == 0 &&
                changedFields == that.changedFields &&
                Double.compare(that.outstanding_shares, outstanding_shares) == 0 &&
                ticker.equals(that.ticker) &&
                currency.equals(that.currency) &&
                dividend_date.containsAll(that.dividend_date) &&
                Objects.equals(valueNotPersistedToDb, that.valueNotPersistedToDb) &&
                quote_timestamp.equals(that.quote_timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker, bid, ask, currency, available_shares, dividend_date, valueNotPersistedToDb, changedFields, outstanding_shares, quote_timestamp);
    }

    @Id
    private LocalDateTime quote_timestamp;

    public static StockQuoteWithAnnotation of(StockQuoteWithAnnotation original) {

        return StockQuoteWithAnnotation.of(
                original.ticker,
                original.bid,
                original.ask,
                original.currency,
                original.available_shares,
                original.outstanding_shares,
                original.quote_timestamp,
                original.dividend_date
        );
    }

    public static StockQuoteWithAnnotation of(String ticker, float bid, float ask, String currency,
                                              double available_shares, double outstanding_shares,
                                              LocalDateTime quote_timestamp,
                                              List<LocalDate> dividend_dates) {

        StockQuoteWithAnnotation i = new StockQuoteWithAnnotation();

        i.setTicker(ticker);
        i.setAsk(ask);
        i.setBid(bid);
        i.setCurrency(currency);
        i.setAvailable_shares(available_shares);
        i.setOutstanding_shares(outstanding_shares);
        i.setQuote_timestamp(quote_timestamp);
        i.setDividend_date(dividend_dates);
        return i;
    }

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
                "quote_timestamp=" + quote_timestamp + "\n" +
                ",ticker='" + ticker + '\'' + "\n" +
                ", bid=" + bid + "\n" +
                ", ask=" + ask + "\n" +
                ", currency='" + currency + '\'' + "\n" +
                ", available_shares=" + available_shares + "\n" +
                ", outstanding_shares=" + outstanding_shares + "\n" +
                ", dividend_date=" + dividend_date + "\n" +
                "}";
    }

    public String getValueNotPersistedToDb() {
        return valueNotPersistedToDb;
    }

    public void setValueNotPersistedToDb(String valueNotPersistedToDb) {
        this.valueNotPersistedToDb = valueNotPersistedToDb;
    }

    @Override
    public Object getPrimaryKey() {
        return getQuote_timestamp();
    }

    @Override
    public String getPrimaryKeyName() {
        return StockQuoteWithAnnotation_.QUOTE_TIMESTAMP;
    }
}
