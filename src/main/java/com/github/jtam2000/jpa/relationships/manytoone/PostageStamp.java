package com.github.jtam2000.jpa.relationships.manytoone;

import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.jtam2000.jpa.relationships.manytoone.PostalCountry_.COUNTRY_ID;


@Entity
public class PostageStamp implements HasPrimaryKey {

    public void setTitle(String title) {

        this.title = title;
    }

    @Id
    @GeneratedValue
    int stampID;
    @ManyToOne
    @JoinColumn(name = COUNTRY_ID)
    private PostalCountry country;

    private double faceValue;
    private String title;
    private LocalDate issueDate;

    public static PostageStamp of(PostalCountry country) {

        String title = "Definitive Forever Stamp";
        LocalDate issueDate = LocalDate.now();
        double faceValue = getRandomFaceValueWithTwoDecimals(0.99D);

        return new PostageStamp(country, faceValue, title, issueDate);
    }

    private static double getRandomFaceValueWithTwoDecimals(double valueBound) {

        double randomValue = ThreadLocalRandom.current().nextDouble(valueBound);
        return getDoubleWithinDecimalPlaces(randomValue, 2);

    }

    private static double getDoubleWithinDecimalPlaces(double value, int decimalPlaces) {

        BigDecimal convertedValue = new BigDecimal(value).setScale(decimalPlaces, RoundingMode.UP);
        return convertedValue.doubleValue();
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public int getStampID() {

        return stampID;
    }


    public PostalCountry getCountry() {

        return country;
    }

    public double getFaceValue() {

        return faceValue;
    }

    public String getTitle() {

        return title;
    }

    public void setCountry(PostalCountry country) {

        this.country = country;
    }

    public LocalDate getIssueDate() {

        return issueDate;
    }


    public PostageStamp(PostalCountry country, double faceValue, String title, LocalDate issueDate) {

        this.country = country;
        this.faceValue = faceValue;
        this.title = title;
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {

        return "\tPostageStamp = {" + "\n" +
                "\t\tstampID=" + stampID + "\n" +
                "\t\tcountry=" + country + "\n" +
                "\t\tfaceValue=" + String.format("%,.2f", faceValue) + "\n" +
                "\t\ttitle='" + title + '\'' + "\n" +
                "\t\tissueDate=" + issueDate + "\n" +
                "\t}";
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostageStamp that = (PostageStamp) o;
        return stampID == that.stampID &&
                Double.compare(that.faceValue, faceValue) == 0 &&
                country == that.country &&
                Objects.equals(title, that.title) &&
                Objects.equals(issueDate, that.issueDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(stampID, country, faceValue, title, issueDate);
    }

    public void setFaceValue(double faceValue) {

        this.faceValue = faceValue;
    }

    //required per JPA specification: kept here for compatible with JPA providers
    @SuppressWarnings({"unused", "RedundantSuppression"})
    protected PostageStamp() {

    }

    @Override
    public Object getPrimaryKey() {

        return stampID;
    }

    @Override
    public String getPrimaryKeyName() {

        return PostageStamp_.STAMP_ID;
    }
}
