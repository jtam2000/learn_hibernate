package com.github.jtam2000.jpa.inheritance.joinedsubclass;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static com.github.jtam2000.jpa.inheritance.joinedsubclass.PostalCountry_.*;
import static javax.persistence.InheritanceType.JOINED;

@Entity(name = "InheritancePhilatelica")
@Inheritance(strategy = JOINED)
public abstract class Philatelica {

    @Id
    @GeneratedValue
    protected int philatelicaID;

    public int getPhilatelicaID() {

        return philatelicaID;
    }

    protected double faceValue;

    public double getFaceValue() {

        return faceValue;
    }

    public void setFaceValue(double faceValue) {

        this.faceValue = faceValue;
    }

    public enum Theme {
        NA,
        PEOPLE,
        EVENTS,
        PLACES,
        ART,
        SCIENCE,
        NATURE,
        OBJECTS,
        DEFINITIVES
    }

    protected String name;
    protected Theme theme;

    @ManyToOne
    @JoinColumn(name = COUNTRY_ID)
    protected PostalCountry country;

    //required by JPA spec
    protected Philatelica() {}

    protected Philatelica(String name, Theme theme, PostalCountry country) {

        this.name = name;
        this.theme = theme;
        this.country = country;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Theme getTheme() {

        return theme;
    }

    public void setTheme(Theme theme) {

        this.theme = theme;
    }

    public PostalCountry getCountry() {

        return country;
    }

    public void setCountry(PostalCountry country) {

        this.country = country;
    }
}
