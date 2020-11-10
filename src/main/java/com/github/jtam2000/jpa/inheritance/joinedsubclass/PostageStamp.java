package com.github.jtam2000.jpa.inheritance.joinedsubclass;

import com.github.jtam2000.jpa.HasPrimaryKey;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import static com.github.jtam2000.jpa.inheritance.joinedsubclass.PostageStamp_.STAMP_ID;

@SuppressWarnings("unused")
@Entity(name = "InheritancePostageStamp")
public class PostageStamp extends Philatelica implements HasPrimaryKey {

    public void setTitle(String title) {

        this.title = title;
    }

    @Id
    @GeneratedValue
    long stampID;

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
    public long getStampID() {

        return stampID;
    }


    public String getTitle() {

        return title;
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
                "\t\tfaceValue=" + String.format("%,.2f", getFaceValue()) + "\n" +
                "\t\ttitle='" + title + '\'' + "\n" +
                "\t\tissueDate=" + issueDate + "\n" +
                "\t}";
    }

    @Override
    public boolean equals(Object o) {

        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        PostageStamp that = (PostageStamp) o;
        return stampID==that.stampID &&
                Double.compare(that.getFaceValue(), getFaceValue())==0 &&
                country==that.country &&
                Objects.equals(title, that.title) &&
                Objects.equals(issueDate, that.issueDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(stampID, country, getFaceValue(), title, issueDate);
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

        return STAMP_ID;
    }
}
