package com.github.jtam2000.testjpa.testbidirectionalonetomany;

import com.github.jtam2000.jpa.dao.JPA;
import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import com.github.jtam2000.jpa.dao.JPARegistry;
import com.github.jtam2000.jpa.relationships.bidirectiononetomany.BiDirectionStampCollection;
import com.github.jtam2000.jpa.relationships.bidirectiononetomany.PostageStamp;
import com.github.jtam2000.jpa.relationships.bidirectiononetomany.PostalCountry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.jtam2000.jpa.relationships.bidirectiononetomany.PostalCountry.Country.ITALY;
import static com.github.jtam2000.jpa.relationships.bidirectiononetomany.PostalCountry.Country.values;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestOneToManyRelationship  {

    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
    private static boolean tearDown = true;
    private static final String jPUString = "jpu_bidirectional_one_to_many";

    private JPADataAccessDaoImpl<BiDirectionStampCollection> dao;
    private JPADataAccessDaoImpl<PostageStamp> stampDao;
    private JPARegistry<PostalCountry> postalRegistry;
    private final Class<BiDirectionStampCollection> targetClass = BiDirectionStampCollection.class;

    private BiDirectionStampCollection stampCollection;

    @Before
    public void runBeforeEachTest() {

        setUpJPUDao();
        deleteRootDao();
        registerAllCountries();
        createBidirectionalItalianStampCollection();

    }

    @After
    public void runAfterEachTest() {

        if (tearDown) {
            deleteRootDao();
        }
        dao.close();
    }

    private void deleteRootDao() {

        stampDao.delete();
        dao.delete();
    }

    private void registerAllCountries() {

        Arrays.stream(values())
                .collect(Collectors.toList())
                .forEach(c -> postalRegistry.getFromRegistry(new PostalCountry(c)));

    }

    private void createBidirectionalItalianStampCollection() {

        stampCollection = new BiDirectionStampCollection("Italian Stamp Collection");
        List<PostageStamp> stampList = createItalianStamps();

        //make direction BiDirectional
        pointStampToCollection(stampList, stampCollection);
        stampCollection.addStamp(stampList);
    }

    private void pointStampToCollection(List<PostageStamp> stampList, BiDirectionStampCollection stampCollection) {

        stampList.forEach(e->e.setKeptInCollection(stampCollection));
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
        stampDao = new JPADataAccessDaoImpl<>(jpa, PostageStamp.class);
    }

    @Test
    //CRud
    public void test_createOneToManyRelationship() {

        //one to many: a stamp collection has many stamps
        //given: @Before persist a collection of Italian stamps

        //when
        dao.create(List.of(stampCollection));
        List<BiDirectionStampCollection> collectionInDb = dao.read();
        List<PostageStamp> createdStampInDb = stampDao.read();

        //then
        int expectedStampCount = stampCollection.getCollection().size();
        assertEquals("stamps in collection should be " + expectedStampCount,
                expectedStampCount,
                collectionInDb.get(0).getCollection().size());

        assertEquals("stamp count in collection should equal count from query",
                expectedStampCount, createdStampInDb.size());
        assertArrayEquals("stamps in collection should equal stamps in query",
                stampCollection.getCollection().toArray(), createdStampInDb.toArray());


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
        BiDirectionStampCollection collection = dao.read().get(0);
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
        stampDao.delete();
        dao.delete();
        List<BiDirectionStampCollection> collection = dao.read();
        List<PostageStamp> stamps = stampDao.read();

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
