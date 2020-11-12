package com.github.jtam2000.jpa.inheritance.tableperconcreateclass;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


@SuppressWarnings("unused")
@Entity(name = "TPCInheritancePostageStamp")
public class PostageStamp extends Philatelica {

    public void setTitle(String title) {

        this.title = title;
    }

    private String title;
    private LocalDate issueDate;

    public static PostageStamp of(PostalCountry country, Theme theme) {

        String title = "Definitive Forever Stamp";
        LocalDate issueDate = LocalDate.now();
        double faceValue = getRandomFaceValueWithTwoDecimals(0.99D);

        PostageStamp stamp = new PostageStamp(country, faceValue, title, issueDate);
        stamp.setTheme(theme);

        return stamp;
    }

    private static double getRandomFaceValueWithTwoDecimals(double valueBound) {

        double randomValue = ThreadLocalRandom.current().nextDouble(valueBound);
        return getDoubleWithinDecimalPlaces(randomValue, 2);

    }

    private static double getDoubleWithinDecimalPlaces(double value, int decimalPlaces) {

        BigDecimal convertedValue = new BigDecimal(value).setScale(decimalPlaces, RoundingMode.UP);
        return convertedValue.doubleValue();
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
                "\t\tphilatelicaID=" + philatelicaID + "\n" +
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
        return
                Double.compare(that.getFaceValue(), getFaceValue())==0 &&
                        country==that.country &&
                        Objects.equals(title, that.title) &&
                        Objects.equals(issueDate, that.issueDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(country, getFaceValue(), title, issueDate);
    }

    public void setIssueDate(LocalDate issueDate) {

        this.issueDate = issueDate;
    }

    //required per JPA specification: kept here for compatible with JPA providers
    @SuppressWarnings({"unused", "RedundantSuppression"})
    protected PostageStamp() {

    }
}
