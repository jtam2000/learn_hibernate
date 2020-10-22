package com.github.jtam2000.jpa.relationships.manytoone;

import com.github.jtam2000.jpa.HasPrimaryKey;
import javafx.geometry.Pos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class PostalCountry implements HasPrimaryKey {

    public enum Country {
        CHINA,
        UNITED_STATES,
        GERMANY,
        FRANCE,
        ITALY,
        TAIWAN,
        HONG_KONG,
        DENMARK;
    }

    public PostalCountry(Country country) {

        countryName = country.toString();
    }

    @Id
    @GeneratedValue
    private int countryID;

    private String countryName;

    @Override
    public Object getPrimaryKey() {

        return countryID;
    }

    @Override
    public boolean equals(Object o) {

        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        PostalCountry that = (PostalCountry) o;
        return countryID==that.countryID &&
                Objects.equals(countryName, that.countryName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(countryID, countryName);
    }

    @Override
    public String toString() {

        return "\tPostalCountry = {" + "\n" +
                "\t\tcountryID=" + countryID + "\n" +
                "\t\tcountryName=" + countryName + "\n" +
                "\t}";
    }

    @Override
    public String getPrimaryKeyName() {

        return PostalCountry_.COUNTRY_ID;
    }
}
