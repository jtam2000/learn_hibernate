package com.github.jtam2000.jpa.relationships.manytomany;

import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Stamps implements HasPrimaryKey {

    @Id
    @GeneratedValue
    long stampID;

    String title;
    String country;
    double faceValue;
    LocalDate issueDate;

    public Stamps(String title, String country, double faceValue, LocalDate issueDate) {

        this.title = title;
        this.country = country;
        this.faceValue = faceValue;
        this.issueDate = issueDate;
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

        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        Stamps stamps = (Stamps) o;
        return stampID==stamps.stampID &&
                Double.compare(stamps.faceValue, faceValue)==0 &&
                Objects.equals(title, stamps.title) &&
                Objects.equals(country, stamps.country) &&
                Objects.equals(issueDate, stamps.issueDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(stampID, title, country, faceValue, issueDate);
    }

    //retained here as specified by JPA Specification
    protected Stamps() {
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
