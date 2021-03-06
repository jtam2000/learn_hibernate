package com.github.jtam2000.jpa.inheritance.joinedsubclass;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Embeddable
public class LengthWidth {
    private int length;
    private int width;

    //required by JPA Spec
    protected LengthWidth() {}

    public int getLength() {

        return length;
    }

    public void setLength(int length) {

        this.length = length;
    }

    public int getWidth() {

        return width;
    }

    public void setWidth(int width) {

        this.width = width;
    }

    public LengthWidth(int length, int width) {

        this.length = length;
        this.width = width;
    }
}
