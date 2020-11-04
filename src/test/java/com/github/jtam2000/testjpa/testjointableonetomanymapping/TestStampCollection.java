package com.github.jtam2000.testjpa.testjointableonetomanymapping;

import com.github.jtam2000.jpa.relationships.jointableonetomany.PostageStamp;
import com.github.jtam2000.jpa.relationships.jointableonetomany.PostalCountry;
import com.github.jtam2000.jpa.relationships.jointableonetomany.StampCollection;
import org.junit.Test;

import java.util.List;

import static com.github.jtam2000.jpa.relationships.jointableonetomany.PostalCountry.Country.ITALY;
import static org.junit.Assert.assertEquals;

public class TestStampCollection {

    private final StampCollection coll = new StampCollection("Italian");

    @Test
    public void test_AddToCollection() {

        PostalCountry italia = new PostalCountry(ITALY);
        List<PostageStamp> stamps = List.of(
                PostageStamp.of(italia),
                PostageStamp.of(italia),
                PostageStamp.of(italia),
                PostageStamp.of(italia),
                PostageStamp.of(italia),
                PostageStamp.of(italia)
        );
        coll.addStamp(stamps);
        assertEquals("number of stamps added should be size of collection:",
                stamps.size(),
                coll.getCollection().size());
    }

    @Test
    public void test_AddDuplicateStampsToCollection() {

        //given and when
        PostageStamp duplicate = addStampListWithTwoDuplicateStamps();

        //then
        long itemCount = coll.getCollection().stream().filter(s -> s.equals(duplicate)).count();
        assertEquals("number of duplicates should be 2", 2L, itemCount);
    }

    private PostageStamp createOneItalianStamp() {

        PostalCountry italy = new PostalCountry(ITALY);
        return PostageStamp.of(italy);
    }

    private PostageStamp addStampListWithTwoDuplicateStamps() {

        PostageStamp repeatStamp = createOneItalianStamp();
        List<PostageStamp> listWithDuplicates = createListOfStampsWithTwoRepeatedStamps(repeatStamp);

        coll.addStamp(listWithDuplicates);

        return repeatStamp;
    }

    private List<PostageStamp> createListOfStampsWithTwoRepeatedStamps(PostageStamp repeatedStamp) {

        return List.of(
                createOneItalianStamp(),
                createOneItalianStamp(),
                createOneItalianStamp(),
                repeatedStamp,
                createOneItalianStamp(),
                repeatedStamp,
                createOneItalianStamp()
        );
    }
}
