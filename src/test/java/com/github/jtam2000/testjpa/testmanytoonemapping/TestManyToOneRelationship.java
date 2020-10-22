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


import static com.github.jtam2000.jpa.relationships.manytoone.PostalCountry.Country.CHINA;
import static com.github.jtam2000.jpa.relationships.manytoone.PostalCountry.Country.UNITED_STATES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class TestManyToOneRelationship {


    private static final double ASSERT_DOUBLE_TOLERANCE = 0.0001;
    private PostageStamp stamp;
    private PostalCountry country;
    private double faceValue;
    private String title;
    private LocalDate issueDate;

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

        createOnePostageStamp(new PostalCountry(CHINA));
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

    private void createOnePostageStamp(PostalCountry whichCountry) {

        country = whichCountry;
        faceValue = 0.80D;
        title = "Celebrating Chinese New Year - Year of the Pig 2019";
        issueDate = LocalDate.of(2019, 2, 18);

        stamp = new PostageStamp(country, faceValue, title, issueDate);
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
    public void test_createOnePostageStamp() {

        //given=@Before

        //when
        createOnePostageStamp((new PostalCountry(CHINA)));

        //then
        assertAllPostageAttributes();
    }

    private void assertAllPostageAttributes() {

        assertEquals("country is set correctly", country, stamp.getCountry());
        assertEquals("face value is set correctly", faceValue, stamp.getFaceValue(), ASSERT_DOUBLE_TOLERANCE);
        assertEquals("title is set correctly", title, stamp.getTitle());
        assertEquals("issue date is set correctly", issueDate, stamp.getIssueDate());
        System.out.println(stamp);
    }

    @Test
    //CRud
    public void test_CreateTwoToOneRelationship() {

        //Many to one: 2 Stamps To that belong to 1 country

        //Given = @Before
        sameCountryStamp = createSecondPostageStamp(stamp.getCountry());
        stampsCreated = List.of(stamp, sameCountryStamp);

        //when
        dao.create(stampsCreated);

        PostageStamp found = dao.findByPrimaryKey(stamp);
        PostageStamp secondFound = dao.findByPrimaryKey(sameCountryStamp);

        //then
        assertEquals("created stamp should equal stamp queried", stamp, found);
        assertEquals("second created stamp should equal second stamp queried", sameCountryStamp, secondFound);
        assertEquals("Country of the two stamp should be the same", found.getCountry(), secondFound.getCountry());

        System.out.println("stamp created and queried:" + found);
        System.out.println("second stamp created and queried:" + secondFound);

    }

    @Test
    //CRud
    public void test_CreateThreeToTwoRelationship() {

        //Given = @Before
        sameCountryStamp = createSecondPostageStamp(stamp.getCountry());
        diffCountryStamp = createThirdPostageStamp(new PostalCountry(UNITED_STATES));
        stampsCreated = List.of(stamp, sameCountryStamp, diffCountryStamp);

        //when
        dao.create(stampsCreated);

        PostageStamp found = dao.findByPrimaryKey(stamp);
        PostageStamp secondFound = dao.findByPrimaryKey(sameCountryStamp);
        PostageStamp thirdFound = dao.findByPrimaryKey(diffCountryStamp);

        //then
        assertEquals("created stamp should equal stamp queried", stamp, found);
        assertEquals("second created stamp should equal second stamp queried", sameCountryStamp, secondFound);
        assertEquals("Country of the two stamp should be the same", found.getCountry(), secondFound.getCountry());
        assertNotEquals("country should be different when stamp is from different country", stamp.getCountry(), diffCountryStamp.getCountry());

        System.out.println("stamp created and queried:" + found);
        System.out.println("second stamp created and queried:" + secondFound);
        System.out.println("third stamp created and queried:" + thirdFound);

    }

    @Test
    //CRud
    public void test_UpdateFromTwoTwoToTwoOneRelationship() {


        //Given = @Before
        diffCountryStamp = createThirdPostageStamp(new PostalCountry(UNITED_STATES));
        stampsCreated = List.of(stamp, diffCountryStamp);
        dao.create(stampsCreated);

        //when
        diffCountryStamp.setCountry(stamp.getCountry());
        dao.update(stampsCreated);
        System.out.println("stamp:" + stamp);
        System.out.println("different country stamp:" + diffCountryStamp);

        //then
//        List<PostalCountry> countryList = postalCountryDao.read();
//        System.out.println("country list:" + countryList);
//        assertEquals("size of country = 1 after update all stamps to same country", 1, countryList.size());
//        assertEquals("country remaining should be " + stamp.getCountry().toString(), stamp.getCountry().toString(),
//                countryList.get(0).toString());
//
//        doNotTearDown();

    }

    private void doNotTearDown() {

        tearDown = false;
    }
}
