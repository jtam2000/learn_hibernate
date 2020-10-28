package com.github.jtam2000.jpa.relationships.manytomany;

import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class Stamp implements HasPrimaryKey {

    @Id
    @GeneratedValue
    private long stampID;

    private String title;
    private String country;
    private double faceValue;
    private LocalDate issueDate;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "jnStampToCollection",
            joinColumns = {@JoinColumn(name = "StampID_FK")},
            inverseJoinColumns = {@JoinColumn(name = "CollectionID_FK")}
    )
    private Set<MyStampCollection> collections=new HashSet<>();

    public Stamp(String title, String country, double faceValue, LocalDate issueDate) {

        this.title = title;
        this.country = country;
        this.faceValue = faceValue;
        this.issueDate = issueDate;
    }

    public Set<MyStampCollection> getCollections() {

        return collections;
    }

    public Set<MyStampCollection> add(MyStampCollection addCollection) {

        collections.add(addCollection);
        return getCollections();
    }

    public static Stamp randomDefinitiveStamp(String country) {

        int year = ThreadLocalRandom.current().nextInt(2000, 2020);
        int month = ThreadLocalRandom.current().nextInt(1, 12);
        int day = ThreadLocalRandom.current().nextInt(1, 28);

        String title = "definitive domestic stamp " + year;
        String cntry = country.toUpperCase();
        double faceValue = generateTwoDecimalFaceValue();

        LocalDate date = LocalDate.of(year, month, day);
        return new Stamp(title,cntry,faceValue,date);

    }
    public static double generateTwoDecimalFaceValue() {
        double faceValue = ThreadLocalRandom.current().nextDouble(0.01, 0.99D);
        faceValue = BigDecimal.valueOf(faceValue)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        return faceValue;
    }

    @Override
    public Object getPrimaryKey() {

        return getStampID();
    }

    @Override
    public String getPrimaryKeyName() {

        return null;
    }

    @Override
    public String toString() {

        return "Stamps{" +
                "stampID=" + stampID +
                ", title='" + title + '\'' +
                ", country='" + country + '\'' +
                ", faceValue=" + faceValue +
                ", issueDate=" + issueDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stamp stamp = (Stamp) o;
        return stampID == stamp.stampID &&
                Double.compare(stamp.faceValue, faceValue) == 0 &&
                Objects.equals(title, stamp.title) &&
                Objects.equals(country, stamp.country) &&
                Objects.equals(issueDate, stamp.issueDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(stampID, title, country, faceValue, issueDate);
    }

    //retained here as specified by JPA Specification
    protected Stamp() {

    }

    public long getStampID() {

        return stampID;
    }

    public void setStampID(long stampID) {

        this.stampID = stampID;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {

        this.country = country;
    }

    public double getFaceValue() {

        return faceValue;
    }

    public void setFaceValue(double faceValue) {

        this.faceValue = faceValue;
    }

    public LocalDate getIssueDate() {

        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {

        this.issueDate = issueDate;
    }
}
