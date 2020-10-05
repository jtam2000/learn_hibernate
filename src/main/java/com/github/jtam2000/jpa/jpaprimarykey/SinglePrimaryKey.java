package com.github.jtam2000.jpa.jpaprimarykey;

import org.apache.commons.math3.analysis.function.Sin;

import javax.annotation.processing.Generated;
import javax.lang.model.util.SimpleAnnotationValueVisitor6;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.concurrent.ThreadLocalRandom;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class SinglePrimaryKey {

    @Id
    //default is @GeneratedValue(strategy = AUTO)
    @GeneratedValue
    private long primaryKey;

    private boolean booleanValue = false;
    private byte byteValue = 0b100001;

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte byteValue) {
        this.byteValue = byteValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public void setCharValue(char charValue) {
        this.charValue = charValue;
    }

    public short getShoreValue() {
        return shoreValue;
    }

    public void setShoreValue(short shoreValue) {
        this.shoreValue = shoreValue;
    }

    public int getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(int integerValue) {
        this.integerValue = integerValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    private char charValue = 'c';
    private short shoreValue = 23;
    private int integerValue = 12_333_444;
    private long longValue = 999_000_111_111L;
    private float floatValue = 23.45F;
    private double doubleValue = 8_000_000.3466D;

    @Override
    public String toString() {
        String sep = System.lineSeparator();
        return "SinglePrimaryKey{" + sep +
                "primaryKey=" + primaryKey + sep +
                ", booleanValue=" + booleanValue +
                ", byteValue=" + byteValue + sep +
                ", charValue=" + charValue + sep +
                ", shoreValue=" + shoreValue + sep +
                ", integerValue=" + integerValue + sep +
                ", longValue=" + longValue + sep +
                ", floatValue=" + floatValue + sep +
                ", doubleValue=" + doubleValue + sep +
                '}' + sep;
    }

    public static SinglePrimaryKey randomInstance() {

        SinglePrimaryKey item = new SinglePrimaryKey();

        item.booleanValue = ThreadLocalRandom.current().nextBoolean();

        int randomInt = ThreadLocalRandom.current().nextInt(Byte.MAX_VALUE);
        item.byteValue = (byte) randomInt;

        randomInt = ThreadLocalRandom.current().nextInt(Short.MAX_VALUE);
        item.shoreValue = (short) randomInt;

        randomInt = ThreadLocalRandom.current().nextInt(Character.MAX_VALUE);
        item.charValue = (char) randomInt;

        randomInt = ThreadLocalRandom.current().nextInt();
        item.integerValue = randomInt;

        item.longValue = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        item.floatValue = ThreadLocalRandom.current().nextFloat();
        item.doubleValue = ThreadLocalRandom.current().nextDouble();


        return item;
    }

    public boolean valuesEqual(SinglePrimaryKey right) {

        return booleanValue == right.booleanValue &&
                    byteValue == right.byteValue &&
            charValue == right.charValue &&
        shoreValue == right.shoreValue &&
        integerValue == right.integerValue &&
        longValue == right.longValue &&
        floatValue == right.floatValue &&
        doubleValue == right.doubleValue;
    }


    public long getPrimaryKey() {
        return primaryKey;
    }
}
