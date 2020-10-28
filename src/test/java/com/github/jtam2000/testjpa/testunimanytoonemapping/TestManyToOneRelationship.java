package com.github.jtam2000.testjpa.testunimanytoonemapping;

import com.github.jtam2000.jpa.HasPrimaryKey;
import com.github.jtam2000.jpa.dao.DataAccessObject;
import com.github.jtam2000.jpa.dao.JPA;
import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import com.github.jtam2000.jpa.dao.JPARegistry;
import com.github.jtam2000.jpa.relationships.manytoone.PostageStamp;
import com.github.jtam2000.jpa.relationships.manytoone.PostalCountry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.jtam2000.jpa.relationships.manytoone.PostalCountry.Country.*;
import static org.junit.Assert.*;


public class TestManyToOneRelationship extends TestPostageStamp {

    public static final double ASSERT_DOUBLE_ERROR=0.0001;

    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
    private static boolean tearDown = true;

    private JPA jpa;
    private JPADataAccessDaoImpl<PostageStamp> dao;
    private final Class<PostageStamp> targetClass = PostageStamp.class;

    private JPARegistry<PostalCountry> countryRegistry;

    private List<PostageStamp> stampsCreated;

    private PostageStamp sameCountryStamp;
    private PostageStamp diffCountryStamp;


    @Before
    public void runOnceBeforeEachTest() {

        setUpJPAThenDaosThenRegisterAllCountries();
        setDefaultPostageStamp(new PostalCountry(HONG_KONG));

    }

    private void emptyAllTablesBeforeTesting() {

        deleteRootDaoThenDependentDao(countryRegistry);
    }

    private void deleteRootDaoThenDependentDao(DataAccessObject<? extends HasPrimaryKey> dependentDao) {

        dao.delete();
        dependentDao.delete();
    }

    @After
    public void tearDown() {

        //let tests have option to not tear down to validate post test situations
        if (tearDown) {
            dao.delete(stampsCreated);
            assertEquals("Tear down table should be zero:",
                    0,
                    dao.read().size());
            countryRegistry.delete();
            assertEquals("Tear down should leave empty Postal Country table",
                    0, countryRegistry.read().size());
        }
        try {
            dao.close();
        } catch (Exception e) {
            System.out.println("closing is causing an exception!!!!");
            System.out.println(e.getMessage());
        }
    }

    private void setUpJPAThenDaosThenRegisterAllCountries() {

        setDaosAndRegistries();
        emptyAllTablesBeforeTesting();
        registerAllCountries();

    }

    private void setDaosAndRegistries() {

        createSharedJPA();
        dao = new JPADataAccessDaoImpl<>(jpa, targetClass);
        countryRegistry = new JPARegistry<>(jpa, PostalCountry.class);
    }

    private void createSharedJPA() {

        String jPUString = "jpu_relationship_many_to_one";
        jpa = new JPA(jPUString);
    }

    private void registerAllCountries() {

        Arrays.stream(values())
                .collect(Collectors.toList())
                .forEach(c -> countryRegistry.getFromRegistry(new PostalCountry(c)));

    }

    private PostageStamp createSecondPostageStamp(PostalCountry whichCountry) {

        country = countryRegistry.getFromRegistry(whichCountry);
        faceValue = 1.12D;
        title = "Joint Issue with United States: Thanksgiving - 2020";
        issueDate = LocalDate.of(2020, 11, 23);

        return new PostageStamp(country, faceValue, title, issueDate);
    }

    private PostageStamp createThirdPostageStamp(PostalCountry whichCountry) {

        country = countryRegistry.getFromRegistry(whichCountry);
        faceValue = .55D;
        title = "Joint Issue with China: Thanksgiving - 2020";
        issueDate = LocalDate.of(2020, 11, 23);

        return new PostageStamp(country, faceValue, title, issueDate);
    }

    @Test
    public void test_RegisterAllCountries() {

        //given: see @Before

        //when
        List<PostalCountry> allCountries = countryRegistry.read();

        //then
        assertEquals("# of countries should be same as size of country enum", PostalCountry.Country.values().length,
                allCountries.size());

        List<PostalCountry> notRegisteredCountries = Arrays.stream(values())
                .filter(v -> !allCountries.contains(new PostalCountry(v)))
                .map(PostalCountry::new)
                .collect(Collectors.toList());

        assertEquals("all countries should be registered in db:", List.of(), notRegisteredCountries);
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

    @Test
    //CRud 3
    public void test_CreateMultipleStampsWithSameCountryInstance() {

        //Given
        stampsCreated = new LinkedList<>();
        PostalCountry denmark = new PostalCountry(DENMARK);
        PostalCountry germany = new PostalCountry(GERMANY);

        addToStampList(PostageStamp.of(denmark));
        addToStampList(PostageStamp.of(denmark));
        addToStampList(PostageStamp.of(new PostalCountry(DENMARK)));

        addToStampList(PostageStamp.of(germany));
        addToStampList(PostageStamp.of(germany));

        //when
        dao.create(stampsCreated);
        List<PostageStamp> createdList = dao.read();

        //then
        assertStampsCreated(createdList, denmark, 3);
        assertStampsCreated(createdList, germany, 2);
    }

    public void assertStampsCreated(List<PostageStamp> createdList, PostalCountry country,
                                    int expectedCounted) {

        assertEquals(country.getPrimaryKey() + " stamps should have been created", expectedCounted,
                createdList.stream()
                        .map(PostageStamp::getCountry)
                        .filter(x -> x.equals(country))
                        .count());
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
        PostalCountry usa = countryRegistry.getFromRegistry(new PostalCountry(UNITED_STATES));

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


        //then

        List<PostageStamp> stampsInDB = dao.read();
        assertTrue("stamps created same as queried from database", stampsInDB.containsAll(stampsCreated));
        assertEquals("# of stamps created same as # in db:", stampsCreated.size(), stampsInDB.size());

        PostageStamp updateStamp = dao.findByPrimaryKey(diffCountryStamp);
        assertEquals("stamp country updated should be the same queried from database", diffCountryStamp.getCountry(),
                updateStamp.getCountry());
        assertEquals("stamp face value updated should be the same queried from database", diffCountryStamp.getFaceValue(),
                updateStamp.getFaceValue(), ASSERT_DOUBLE_ERROR);
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
        countryRegistry.delete();

        //then
        assertEquals("zero postage stamps after delete", 0, dao.read().size());
        assertEquals("zero postal country after delete", 0, countryRegistry.read().size());

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
        countryRegistry.delete();

        //then
        assertEquals("zero postage stamps after delete", 0, dao.read().size());
        assertEquals("zero postal country after delete", 0, countryRegistry.read().size());

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
