package com.github.jtam2000.jpa.inheritance.joinedsubclass;

import javax.persistence.Entity;

@Entity(name = "InheritancePostCard")
public class PostCard extends Philatelica{

    public LengthWidth getSize() {

        return size;
    }

    public void setSize(LengthWidth size) {

        this.size = size;
    }

    private LengthWidth size;

    //Required by JPA specification
    protected PostCard() {}

}
