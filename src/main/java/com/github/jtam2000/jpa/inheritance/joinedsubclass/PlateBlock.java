package com.github.jtam2000.jpa.inheritance.joinedsubclass;

import javax.persistence.Entity;

@Entity
public class PlateBlock extends PostageStamp {

    private LengthWidth dimension;
    private PlateBlockAttribute blockAttribute;

    public PlateBlock(LengthWidth dimension, PlateBlockAttribute blockAttribute) {

        this.dimension = dimension;
        this.blockAttribute = blockAttribute;
    }
}
