package com.github.jtam2000.jpa.inheritance.joinedsubclass;

import javax.persistence.Entity;

@Entity
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
}
