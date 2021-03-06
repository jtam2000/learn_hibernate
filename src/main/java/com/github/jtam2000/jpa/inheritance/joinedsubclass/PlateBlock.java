package com.github.jtam2000.jpa.inheritance.joinedsubclass;

import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.Entity;

@Entity(name = "InheritancePlateBlock")
public class PlateBlock extends PostageStamp implements HasPrimaryKey {

    private LengthWidth dimension;
    private PlateBlockAttribute blockAttribute;

    public PlateBlock(LengthWidth dimension, PlateBlockAttribute blockAttribute) {

        this.dimension = dimension;
        this.blockAttribute = blockAttribute;
    }
    //required by JPA Spec
    @SuppressWarnings("unused")
    protected  PlateBlock(){}



    @Override
    public Object getPrimaryKey() {

        return super.getPrimaryKey();
    }

    @Override
    public String getPrimaryKeyName() {

        return super.getPrimaryKeyName();
    }
}
