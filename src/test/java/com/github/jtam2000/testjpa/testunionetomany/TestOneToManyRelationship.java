package com.github.jtam2000.testjpa.testunionetomany;

import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import com.github.jtam2000.jpa.relationships.manytoone.PostageStamp;
import com.github.jtam2000.jpa.relationships.manytoone.PostalCountry;
import com.github.jtam2000.jpa.relationships.onetomany.StampCollection;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static com.github.jtam2000.jpa.relationships.manytoone.PostalCountry.Country.ITALY;

public class TestOneToManyRelationship extends TestStampCollection {

    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
    private static boolean tearDown = true;

    private final String jPUString = "jpu_relationship_one_to_many";
    private JPADataAccessDaoImpl<StampCollection> dao;
    private final Class<StampCollection> targetClass = StampCollection.class;

    private StampCollection stampCollection;

    @Before
    public void runBeforeEachTest() {

        createItalianStampCollection();
        setUpJPUDao();

    }

    private void createItalianStampCollection() {

        stampCollection = new StampCollection("Italian Stamp Collection");
        List<PostageStamp> stampList = createItalianStamps();
        stampCollection.addStamp(stampList);
    }

    private List<PostageStamp> createItalianStamps() {

        PostalCountry italy = findCountry(new PostalCountry(ITALY));
        return List.of(
                PostageStamp.of(italy),
                PostageStamp.of(italy),
                PostageStamp.of(italy),
                PostageStamp.of(italy),
                PostageStamp.of(italy),
                PostageStamp.of(italy)
        );

    }

    private PostalCountry findCountry(PostalCountry country) {
        JPADataAccessDaoImpl<PostalCountry> countryDao = new JPADataAccessDaoImpl<PostalCountry>(jPUString,
                PostalCountry.class);
        PostalCountry foundCountry = countryDao.findByPrimaryKey(country);
        return  (Objects.nonNull(foundCountry)) ? foundCountry: country;

    }

    private void setUpJPUDao() {

        dao = new JPADataAccessDaoImpl<>(jPUString, targetClass);
    }

    @Test
    //CRud
    public void test_createOneToManyRelationship() {

        //one to many: a stamp collection has many stamps

        //given: @Before persist a collection of Italian stamps

        System.out.println("collection:" + stampCollection.getCollection());
        //when
        dao.create(List.of(stampCollection));
    }
}
