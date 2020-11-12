package com.github.jtam2000.jpa.inheritance.tableperconcreateclass;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class PlateBlockAttribute {

    public enum PlateNumberLocation {
        TOP_LEFT,
        TOP_RIGHT,
        MID_LEFT,
        MID_RIGHT,
        BOT_LEFT,
        BOT_RIGHT
    }

    public String plateNumber;
    public boolean plateNumberHasColor;
    @Enumerated(EnumType.STRING)
    public PlateNumberLocation plateNumberLocation;

    protected int plateBlockID;

    //required by JPA Spec
    protected PlateBlockAttribute() {

    }

    public PlateBlockAttribute(String pn, boolean hasColor,
                               PlateNumberLocation location) {
        this.plateNumber =pn;
        this.plateNumberHasColor = hasColor;
        this.plateNumberLocation = location;

    }
}
