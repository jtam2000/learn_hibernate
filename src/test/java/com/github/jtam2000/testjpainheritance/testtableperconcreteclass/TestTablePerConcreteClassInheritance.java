package com.github.jtam2000.testjpainheritance.testtableperconcreteclass;

import com.github.jtam2000.jpa.dao.JPA;
import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import com.github.jtam2000.jpa.dao.JPARegistry;
import com.github.jtam2000.jpa.inheritance.tableperconcreateclass.PlateBlock;
import com.github.jtam2000.jpa.inheritance.tableperconcreateclass.PostCard;
import com.github.jtam2000.jpa.inheritance.tableperconcreateclass.PostageStamp;
import com.github.jtam2000.jpa.inheritance.tableperconcreateclass.PostalCountry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


public class TestTablePerConcreteClassInheritance {

    private static final String jPUString = "jpu_inheritance_table_per_concrete_class";

    private JPADataAccessDaoImpl<PostageStamp> dao;
    private JPARegistry<PostalCountry> postalRegistry;
    private final Class<PostageStamp> targetClass = PostageStamp.class;

    private JPADataAccessDaoImpl<PlateBlock> pblDao;
    private JPADataAccessDaoImpl<PostCard> pcDao;

    private static boolean tearDown = true;


    @Before
    public void runBeforeEachTest() {

        setUpJPUDao();
        registerAllCountries();
        pblDao.delete();
        pcDao.delete();
        dao.delete();
    }

    @After
    public void runAfterEachTest() {

        if (tearDown) {
            pblDao.delete();
            pcDao.delete();
            dao.delete();
        }
        dao.close();
    }

    private void registerAllCountries() {

        Arrays.stream(PostalCountry.Country.values())
                .collect(Collectors.toList())
                .forEach(c -> postalRegistry.getFromRegistry(new PostalCountry(c)));
    }

    private void setUpJPUDao() {

        JPA jpa = new JPA(jPUString);

        dao = new JPADataAccessDaoImpl<>(jpa, targetClass);
        postalRegistry = new JPARegistry<>(jpa, PostalCountry.class);

        pblDao = new JPADataAccessDaoImpl<>(jpa, PlateBlock.class);
        pcDao = new JPADataAccessDaoImpl<>(jpa, PostCard.class);
    }

    @Test
    public void test_createPostageStamp() {

        PostageStamp expected = createSamplePostageStamp();

        List<PostageStamp> readIn = dao.read();
        assertEquals(1, readIn.size());
        assertEquals(expected, readIn.get(0));
    }

    private PostageStamp createSamplePostageStamp() {

        PostageStamp expected = PostageStamp.samplePostageStamp();
        dao.create(List.of(expected));
        return expected;
    }

    @Test
    public void test_createPlateBlock() {

        PlateBlock expectedPB = createSamplePlateBlock();

        List<PlateBlock> readIn = pblDao.read();
        assertEquals(1, readIn.size());
        assertEquals(expectedPB, readIn.get(0));
    }

    private PlateBlock createSamplePlateBlock() {

        PlateBlock expectedPB = PlateBlock.samplePlateBlock();
        pblDao.create(List.of(expectedPB));
        return expectedPB;
    }

    @Test
    public void test_createPostCard() {

        PostCard expected = createSamplePostCard();

        List<PostCard> readIn = pcDao.read();
        assertEquals(1, readIn.size());
        assertEquals(expected, readIn.get(0));

    }

    private PostCard createSamplePostCard() {

        PostCard expected = PostCard.sampleCard();
        pcDao.create(List.of(expected));
        return expected;
    }

    @Test
    public void test_createAllThreePhilatelica() {


        PostageStamp expected = createSamplePostageStamp();
        PlateBlock expectedPB = createSamplePlateBlock();
        PostCard expectedPC = createSamplePostCard();

        //assert Stamp
        List<PostageStamp> readInStamp = dao.read();
        //LEARNING: expect 2 because PlateBlock creates an entry in PostageStamp table
        //  PlateBlock inherits from PostageStamp
        assertEquals(2, readInStamp.size());
        assertEquals(expected, readInStamp.get(0));

        //assert PlateBlock
        List<PlateBlock> readInPlateBlock = pblDao.read();
        assertEquals(1, readInPlateBlock.size());
        assertEquals(expectedPB, readInPlateBlock.get(0));

        //assert PostCard
        List<PostCard> readInPostCard = pcDao.read();
        assertEquals(1, readInPostCard.size());
        assertEquals(expectedPC, readInPostCard.get(0));
    }

    @SuppressWarnings("unused")
    private void doNotTearDown() {

        tearDown = false;
    }
}
