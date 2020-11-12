package com.github.jtam2000.jpa.inheritance.joinedsubclass;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Embeddable
public class PlateBlockAttribute {

    public enum PlateNumberLocation{
        TOP_LEFT,
        TOP_RIGHT,
        MID_LEFT,
        MID_RIGHT,
        BOT_LEFT,
        BOT_RIGHT
    }

    public String plateNumber;
    public boolean plateNumberHasColor;
    public PlateNumberLocation plateNumberLocation;

    protected int plateBlockID;

    //required by JPA Spec
    protected PlateBlockAttribute(){}
}
