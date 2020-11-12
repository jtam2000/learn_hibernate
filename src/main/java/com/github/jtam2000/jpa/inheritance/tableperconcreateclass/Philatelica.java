package com.github.jtam2000.jpa.inheritance.tableperconcreateclass;


import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static com.github.jtam2000.jpa.inheritance.tableperconcreateclass.PostalCountry_.COUNTRY_ID;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity(name = "TPCInheritancePhilatelica")

//LEARNING: The inheritance @annotation must be placed at the root class
//
//LEARNING: Because this is not a concrete class, no table will be created for this class
// instead the fields in this class will get installed as columns on the tables of the concrete class
// that extends this abstract class (for example: PostageStamp, PlateBlock and PostCard
//
@Inheritance(strategy = TABLE_PER_CLASS)
public abstract class Philatelica  implements HasPrimaryKey {

    @Id
    @GeneratedValue
    protected int philatelicaID;

    protected double faceValue;

    public double getFaceValue() {

        return faceValue;
    }

    public void setFaceValue(double faceValue) {

        this.faceValue = faceValue;
    }

    @Override
    public Object getPrimaryKey() {

        return philatelicaID;
    }

    @Override
    public String getPrimaryKeyName() {

        return com.github.jtam2000.jpa.inheritance.joinedsubclass.Philatelica_.PHILATELICA_ID;
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
    @Enumerated(EnumType.STRING)
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
