package com.github.jtam2000.testjpa.testmanytoonemapping;

import com.github.jtam2000.jpa.HasPrimaryKey;
import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import com.github.jtam2000.jpa.relationships.manytoone.PostageStamp;
import com.github.jtam2000.jpa.relationships.manytoone.PostalCountry;
import com.github.jtam2000.stockquotes.DataAccessObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;


import static com.github.jtam2000.jpa.relationships.manytoone.PostalCountry.Country.*;
import static org.junit.Assert.*;


public class TestManyToOneRelationship extends TestPostageStamp {

    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
    private static boolean tearDown = true;

    private final String jPUString = "jpu_relationship_many_to_one";
    private JPADataAccessDaoImpl<PostageStamp> dao;
    private final Class<PostageStamp> targetClass = PostageStamp.class;

    private final JPADataAccessDaoImpl<PostalCountry> postalCountryDao =
            new JPADataAccessDaoImpl<>(jPUString, PostalCountry.class);

    private List<PostageStamp> stampsCreated;

    private PostageStamp sameCountryStamp;
    private PostageStamp diffCountryStamp;

    @Before
    public void runBeforeEachTest() {

        createOnePostageStamp(new PostalCountry(HONG_KONG));
        setUpJPUDao();
        emptyAllTablesBeforeTesting();
    }

    private void emptyAllTablesBeforeTesting() {

        deleteRootDaoThenDependentDao(postalCountryDao);
    }

    private void deleteRootDaoThenDependentDao(DataAccessObject<? extends HasPrimaryKey> dependentDao) {

        dao.delete();
        dependentDao.delete();
    }

    @After
    public void tearDown() throws Exception {

        //let tests have option to not tear down to validate post test situations
        if (tearDown) {
            dao.delete(stampsCreated);
            assertEquals("Tear down table should be zero:",
                    0,
                    dao.read().size());
            assertEquals("Tear down should leave empty Postal Country table",
                    0, postalCountryDao.read().size());
        }

        dao.close();
    }

    private void setUpJPUDao() {

        dao = new JPADataAccessDaoImpl<>(jPUString, targetClass);
    }

    private PostageStamp createSecondPostageStamp(PostalCountry whichCountry) {

        country = whichCountry;
        faceValue = 1.12D;
        title = "Joint Issue with United States: Thanksgiving - 2020";
        issueDate = LocalDate.of(2020, 11, 23);

        return new PostageStamp(country, faceValue, title, issueDate);
    }

    private PostageStamp createThirdPostageStamp(PostalCountry whichCountry) {

        country = whichCountry;
        faceValue = .55D;
        title = "Joint Issue with China: Thanksgiving - 2020";
        issueDate = LocalDate.of(2020, 11, 23);

        return new PostageStamp(country, faceValue, title, issueDate);
    }

    @Test
    //CRud 1
    public void test_CreateTwoToOneRelationship() {

        //Many to one: 2 Stamps To that belong to same 1 country

        //Given = @Before
        sameCountryStamp = createSecondPostageStamp(stamp.getCountry());
        stampsCreated = List.of(stamp, sameCountryStamp);

        //when
        dao.create(stampsCreated);

        //then
        assertCreatedStampByQuery();
    }

    private void assertCreatedStampByQuery() {

        PostageStamp found = dao.findByPrimaryKey(stamp);
        PostageStamp secondFound = dao.findByPrimaryKey(sameCountryStamp);
        List<PostageStamp> items = dao.read();

        assertEquals("There should be two items from the create:", 2, items.size());
        assertTrue("query should contain stamps created", items.containsAll(stampsCreated));
        assertEquals("created stamp should equal stamp queried", stamp, found);
        assertEquals("second created stamp should equal second stamp queried", sameCountryStamp, secondFound);
        assertEquals("Country of the two stamp should be the same", found.getCountry(), secondFound.getCountry());
    }

    @Test
    //CRud 2
    public void test_CreateThreeToTwoRelationship() {

        //Given,  @Before has the first stamp
        stampsCreated = addTwoMoreStampsToExistingStamp();

        //when
        dao.create(stampsCreated);

        //then
        assertThreeStampsCreated();
    }

    private void assertThreeStampsCreated() {

        //then
        PostageStamp found = dao.findByPrimaryKey(stamp);
        PostageStamp secondFound = dao.findByPrimaryKey(sameCountryStamp);
        PostageStamp thirdFound = dao.findByPrimaryKey(diffCountryStamp);

        List<PostageStamp> stampsInDB = dao.read();

        assertEquals("three stamps created", 3, stampsInDB.size());
        assertTrue("stamps created should be same as quried", stampsInDB.containsAll(stampsInDB));
        assertEquals("created stamp should equal stamp queried", stamp, found);
        assertEquals("second created stamp should equal second stamp queried", sameCountryStamp, secondFound);
        assertEquals("Country of the two stamp should be the same", found.getCountry(), secondFound.getCountry());
        assertNotEquals("country should be different when stamp is from different country", stamp.getCountry(), diffCountryStamp.getCountry());

    }
    private List<PostageStamp> addTwoMoreStampsToExistingStamp() {

        sameCountryStamp = createSecondPostageStamp(stamp.getCountry());
        PostalCountry usa = new PostalCountry(UNITED_STATES);
        diffCountryStamp = createThirdPostageStamp(usa);
        return List.of(stamp, sameCountryStamp, diffCountryStamp);
    }

    @Test
    //crUd
    public void test_UpdateFromTwoTwoToTwoOneRelationship() {

        //Given = @Before
        PostalCountry usa = new PostalCountry(UNITED_STATES);
        diffCountryStamp = createThirdPostageStamp(usa);
        stampsCreated = List.of(stamp, diffCountryStamp);
        dao.create(stampsCreated);

        //when
        diffCountryStamp.setCountry(stamp.getCountry());
        diffCountryStamp.setFaceValue(111D);
        dao.update(stampsCreated);

        //delete the old country, JPA does not cascade this delete
        forceDeleteCountryNotCascadeDeleteByJPA(usa);


        //then
        List<PostalCountry> countryList = postalCountryDao.read();
        assertEquals("size of country = 1 after update all stamps to same country", 1, countryList.size());
        assertEquals("country remaining should be " + stamp.getCountry().toString(), stamp.getCountry().toString(),
                countryList.get(0).toString());

        List<PostageStamp> stampsInDB = dao.read();
        assertTrue("stamps created same as queried from database", stampsInDB.containsAll(stampsInDB));
        assertEquals("# of stamps created same as # in db:", stampsCreated.size(), stampsInDB.size());
    }

    private void forceDeleteCountryNotCascadeDeleteByJPA(PostalCountry usa) {

        postalCountryDao.delete(List.of(usa));
    }

    private void doNotTearDown() {

        tearDown = false;
    }

    @Test
    //cruD 1
    public void test_DeleteRelationship() {

        //Given = @Before
        createFewStampsFromDifferentCountries();
        dao.create(stampsCreated);

        //when
        dao.delete(stampsCreated);

        //then
        assertEquals("zero postage stamps after delete", 0, dao.read().size());
        assertEquals("zero postal country after delete", 0, postalCountryDao.read().size());

        //we already delete everything, so do not teardown again
        preventDoubleTearDown();
    }


    @Test
    //cruD 2
    public void test_DeleteTwoToOneRelationship() {

        //Given = @Before
        //two stamps from the same country => 1 postal country: 2 stamp to 1 country
        stampsCreated = List.of(stamp, createSecondPostageStamp(stamp.getCountry()));

        //when
        dao.delete(stampsCreated);

        //then
        assertEquals("zero postage stamps after delete", 0, dao.read().size());
        assertEquals("zero postal country after delete", 0, postalCountryDao.read().size());

        //we already delete everything, so do not teardown again
        preventDoubleTearDown();
    }

    private void preventDoubleTearDown() {

        doNotTearDown();
    }

    private void createFewStampsFromDifferentCountries() {

        PostalCountry denmark = new PostalCountry(DENMARK);
        diffCountryStamp = createThirdPostageStamp(new PostalCountry(UNITED_STATES));
        stampsCreated = List.of(stamp, diffCountryStamp,
                createThirdPostageStamp(denmark),
                createSecondPostageStamp(denmark));
    }

}
