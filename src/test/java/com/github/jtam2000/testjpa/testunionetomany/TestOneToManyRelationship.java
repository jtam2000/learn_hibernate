package com.github.jtam2000.testjpa.testunionetomany;

import com.github.jtam2000.jpa.HasPrimaryKey;
import com.github.jtam2000.jpa.dao.DataAccessObject;
import com.github.jtam2000.jpa.dao.JPA;
import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import com.github.jtam2000.jpa.dao.JPARegistry;
import com.github.jtam2000.jpa.relationships.manytoone.PostageStamp;
import com.github.jtam2000.jpa.relationships.manytoone.PostalCountry;
import com.github.jtam2000.jpa.relationships.onetomany.StampCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.jtam2000.jpa.relationships.manytoone.PostalCountry.Country.ITALY;
import static com.github.jtam2000.jpa.relationships.manytoone.PostalCountry.Country.values;
import static org.junit.Assert.assertEquals;

public class TestOneToManyRelationship extends TestStampCollection {

    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
    private static boolean tearDown = true;

    private final String jPUString = "jpu_relationship_one_to_many";
    private JPADataAccessDaoImpl<StampCollection> dao;
    private JPARegistry<PostalCountry> postalRegistry;
    private final Class<StampCollection> targetClass = StampCollection.class;

    private StampCollection stampCollection;

    @Before
    public void runBeforeEachTest() {

        setUpJPUDao();
        deleteRootDao();
        registerAllCountries();
        createItalianStampCollection();

    }

    @After
    public void runAfterEachTest() {

        if (tearDown) {
            deleteRootDao();
        }
        dao.close();
    }

    private void deleteRootDao() {

        dao.delete();
    }

    private void registerAllCountries() {

        Arrays.stream(values())
                .collect(Collectors.toList())
                .forEach(c -> postalRegistry.getFromRegistry(new PostalCountry(c)));

    }

    private void createItalianStampCollection() {

        stampCollection = new StampCollection("Italian Stamp Collection");
        List<PostageStamp> stampList = createItalianStamps();
        stampCollection.addStamp(stampList);
    }

    private List<PostageStamp> createItalianStamps() {

        PostalCountry italy = new PostalCountry(ITALY);
        return List.of(
                PostageStamp.of(italy),
                PostageStamp.of(italy),
                PostageStamp.of(italy),
                PostageStamp.of(italy),
                PostageStamp.of(italy),
                PostageStamp.of(italy)
        );

    }

    private void setUpJPUDao() {

        JPA jpa = new JPA(jPUString);
        dao = new JPADataAccessDaoImpl<>(jpa, targetClass);
        postalRegistry = new JPARegistry<>(jpa, PostalCountry.class);
    }

    @Test
    //CRud
    public void test_createOneToManyRelationship() {

        //one to many: a stamp collection has many stamps
        //given: @Before persist a collection of Italian stamps

        //when
        dao.create(List.of(stampCollection));
        List<StampCollection> collection = dao.read();

        //then
        int expectedStampCount = stampCollection.getCollection().size();
        assertEquals("stamps in collection should be " + expectedStampCount,
                expectedStampCount,
                collection.get(0).getCollection().size());
    }


    @Test
    //crUd
    public void test_updateOneToManyRelationship() {

        //given
        String updatedTitle = "Definitive Stamp: updated "+ LocalDate.now().getYear();

        //when
        setAllStampTitle(updatedTitle);

        //then
        String actualTitle =dao.read().get(0).getCollection().get(4).getTitle();
        assertEquals("updated title should be the same as queried from db: ", updatedTitle, actualTitle);

    }

    private void setAllStampTitle(String updatedTitle) {

        dao.create(List.of(stampCollection));
        StampCollection collection = dao.read().get(0);
        collection.getCollection()
                .forEach(s -> s.setTitle(updatedTitle));
    }

    @Test
    //cruD
    public void test_deleteOneToManyRelationship() {

        //one to many: a stamp collection has many stamps
        //given: @Before persist a collection of Italian stamps

        //when
        dao.create(List.of(stampCollection));
        dao.delete();
        List<StampCollection> collection = dao.read();

        //then
        int expectedStampCount = 0;
        int actual = collection.size() == 0 ? 0 : collection.get(0).getCollection().size();
        assertEquals("stamps in collection should be zero after delete",
                expectedStampCount,
                actual);

        doNotTearDown();
    }

    private void doNotTearDown() {

        tearDown = false;
    }
}
