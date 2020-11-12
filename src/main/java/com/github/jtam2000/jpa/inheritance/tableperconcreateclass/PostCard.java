package com.github.jtam2000.jpa.inheritance.tableperconcreateclass;

import javax.persistence.Entity;

@Entity(name = "TPCInheritancePostCard")
public class PostCard extends Philatelica {

    public LengthWidth getSize() {

        return size;
    }

    public void setSize(LengthWidth size) {

        this.size = size;
    }

    private LengthWidth size;

    //Required by JPA specification
    @SuppressWarnings("unused")
    protected PostCard() {}

    public PostCard(String name, Theme theme, PostalCountry country, LengthWidth size) {

        super(name, theme, country);
        this.size = size;
    }

    public static PostCard sampleCard() {

        PostCard card = new PostCard("Grand Canyon National Park",
                Theme.NATURE,
                new PostalCountry(PostalCountry.Country.UNITED_STATES),
                new LengthWidth(23, 45)
        );
        card.setFaceValue(0.52);
        return card;
    }
}
