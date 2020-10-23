package com.github.jtam2000.testjpa.testunimanytoonemapping;

import com.github.jtam2000.jpa.HasPrimaryKey;
import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import com.github.jtam2000.jpa.relationships.manytoone.PostageStamp;
import com.github.jtam2000.jpa.relationships.manytoone.PostalCountry;
import com.github.jtam2000.stockquotes.DataAccessObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static com.github.jtam2000.jpa.relationships.manytoone.PostalCountry.Country.DENMARK;
import static com.github.jtam2000.jpa.relationships.manytoone.PostalCountry.Country.GERMANY;
import static com.github.jtam2000.jpa.relationships.manytoone.PostalCountry.Country.HONG_KONG;
import static com.github.jtam2000.jpa.relationships.manytoone.PostalCountry.Country.UNITED_STATES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


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
    public void runOnceBeforeEachTest() {

        setDefaultPostageStamp(new PostalCountry(HONG_KONG));
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
    public void tearDown(){

        //let tests have option to not tear down to validate post test situations
        if (tearDown) {
            dao.delete(stampsCreated);
            assertEquals("Tear down table should be zero:",
                    0,
                    dao.read().size());
            assertEquals("Tear down should leave empty Postal Country table",
                    0, postalCountryDao.read().size());
        }
        try {
            dao.close();
        }catch (Exception e) {
            System.out.println("closing is causing an exception!!!!");
            System.out.println(e.getMessage());
        }
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

    @Test(expected = EntityExistsException.class)
    //CRud 3
    public void test_CreateMultipleStampsWithSameCountryDiffInstance_CausesEntityExistsException() {


        //Given
        stampsCreated = new LinkedList<>();
        PostalCountry denmark = new PostalCountry(DENMARK);
        PostalCountry germany = new PostalCountry(GERMANY);
        addToStampList(PostageStamp.of(denmark));
        addToStampList(PostageStamp.of(denmark));

        //duplicates country entity generates EntityExistsException
        addToStampList(PostageStamp.of(new PostalCountry(DENMARK)));

        addToStampList(PostageStamp.of(germany));
        addToStampList(PostageStamp.of(germany));

        //then, when
        manageEntityExistsException();

    }

    private void manageEntityExistsException() {

        try {
            dao.create(stampsCreated);

        } catch (EntityExistsException e) {

            //no @After teardown because we need to unwind the transaction
            doNotTearDown();
            rollbackRootDaoThenDependentDao(e, postalCountryDao);

        } finally {
            assertThatTransactionExceptionMeansNoDataInsertion();
        }
    }

    private void assertThatTransactionExceptionMeansNoDataInsertion() {

        List<PostageStamp> stampInDb = dao.read();
        List<PostalCountry> countriesInDb = postalCountryDao.read();
        assertEquals("after rollback Stamp Count should be zero", 0, stampInDb.size());
        assertEquals("after rollback Country count should be zero", 0, countriesInDb.size());
    }

    private void rollbackRootDaoThenDependentDao(EntityExistsException e, JPADataAccessDaoImpl<PostalCountry> dependentDao) {

        System.out.println("rolling back root dao transaction");
        dao.rollbackTransaction();

        System.out.println("rolling back dependent dao transaction");
        dependentDao.rollbackTransaction();
        throw e;
    }

    private void addToStampList(PostageStamp addend) {

        stampsCreated.add(addend);
    }

    private void assertThreeStampsCreated() {

        //then
        PostageStamp found = dao.findByPrimaryKey(stamp);
        PostageStamp secondFound = dao.findByPrimaryKey(sameCountryStamp);
        PostageStamp thirdFound = dao.findByPrimaryKey(diffCountryStamp);

        List<PostageStamp> stampsInDB = dao.read();

        assertEquals("three stamps created", 3, stampsInDB.size());
        assertTrue("stamps created should be same as queried", stampsInDB.containsAll(stampsCreated));
        assertEquals("created stamp should equal stamp queried", stamp, found);
        assertEquals("second created stamp should equal second stamp queried", sameCountryStamp, secondFound);
        assertEquals("third created stamp should equal third stamp queried", diffCountryStamp, thirdFound);

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
        assertTrue("stamps created same as queried from database", stampsInDB.containsAll(stampsCreated));
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
