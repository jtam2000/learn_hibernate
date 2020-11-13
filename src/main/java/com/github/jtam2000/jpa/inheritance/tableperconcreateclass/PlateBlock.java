package com.github.jtam2000.jpa.inheritance.tableperconcreateclass;

import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.Entity;

import java.time.LocalDate;

import static com.github.jtam2000.jpa.inheritance.tableperconcreateclass.PlateBlockAttribute.PlateNumberLocation.BOT_RIGHT;

@Entity(name = "TPCInheritancePlateBlock")
public class PlateBlock extends PostageStamp {

    private LengthWidth dimension;
    private PlateBlockAttribute blockAttribute;

    public PlateBlock(LengthWidth dimension, PlateBlockAttribute blockAttribute) {

        this.dimension = dimension;
        this.blockAttribute = blockAttribute;
    }
    //required by JPA Spec
    @SuppressWarnings("unused")
    protected  PlateBlock(){}


    public static PlateBlock samplePlateBlock() {

        LengthWidth lw = new LengthWidth(20, 30);
        PlateBlockAttribute pba = new PlateBlockAttribute("NP34451111", false, BOT_RIGHT);

        PlateBlock plateBlock = new PlateBlock(lw, pba);
        plateBlock.setTitle("Four Corners");
        plateBlock.setTheme(Philatelica.Theme.PLACES);
        plateBlock.setIssueDate(LocalDate.of(2019, 6, 25));
        return plateBlock;
    }

    @Override
    public Object getPrimaryKey() {

        return super.getPrimaryKey();
    }

    @Override
    public String getPrimaryKeyName() {

        return super.getPrimaryKeyName();
    }
}
