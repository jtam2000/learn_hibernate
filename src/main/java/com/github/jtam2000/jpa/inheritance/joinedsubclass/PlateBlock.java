package com.github.jtam2000.jpa.inheritance.joinedsubclass;

import javax.persistence.Entity;

@Entity(name = "InheritancePlateBlock")
public class PlateBlock extends PostageStamp {

    private LengthWidth dimension;
    private PlateBlockAttribute blockAttribute;

    public PlateBlock(LengthWidth dimension, PlateBlockAttribute blockAttribute) {
        super();

        this.dimension = dimension;
        this.blockAttribute = blockAttribute;
    }
    //required by JPA Spec
    @SuppressWarnings("unused")
    protected  PlateBlock(){}
}
