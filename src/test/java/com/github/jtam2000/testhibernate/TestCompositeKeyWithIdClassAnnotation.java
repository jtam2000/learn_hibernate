package com.github.jtam2000.testhibernate;

import com.github.jtam2000.jpa.compositeprimarykey.CompositeKeyWithIdClassAnnotation;
import com.github.jtam2000.jpa.dao.JPA;
import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestCompositeKeyWithIdClassAnnotation {

    final private String jpuName = "jpu_composite_primarykey_id_class";

    private final JPA jpa = new JPA(jpuName);

    private JPADataAccessDaoImpl<CompositeKeyWithIdClassAnnotation> dao;
    private final Class<CompositeKeyWithIdClassAnnotation> targetClass = CompositeKeyWithIdClassAnnotation.class;

    private CompositeKeyWithIdClassAnnotation oneQuote;

    private boolean tearDown = true;

    @Before
    public void setup() {

        dao = new JPADataAccessDaoImpl<>(jpa, targetClass);

        //blank table before every test
        dao.delete();

        //create one example
        oneQuote = CompositeKeyWithIdClassAnnotation.of();
        oneQuote.setNetWorth(1);

        assertEquals("Set up table should be zero:", 0, dao.read().size());
    }

    @After
    public void tearDown() {

        //let tests have option to not tear down to validate post test situations
        if (tearDown) {
            dao.delete();
            assertEquals("Tear down table should be zero:",
                    0,
                    dao.read().size());
        }

        jpa.close();
    }

    //Crud
    @Test
    public void testCreate_OneQuote() {

        oneQuote.setNetWorth(1_500_000.45);
        dao.create(List.of(oneQuote));

        List<CompositeKeyWithIdClassAnnotation> listInDB = dao.read();
        assertEquals("row count:", 1, listInDB.size());
        assertEquals("row content:", oneQuote, listInDB.get(0));
        tearDown = true;

    }

    //cRud
    @Test
    public void testRead_OneQuote() {

        //given
        List<CompositeKeyWithIdClassAnnotation> quotes = List.of(oneQuote);
        oneQuote.setNetWorth(200_000_000);
        dao.create(quotes);

        List<CompositeKeyWithIdClassAnnotation> readBackCopies = dao.readByPrimaryKey(quotes);

        assertEquals("size of persistent story is 1:", 1, readBackCopies.size());
        assertEquals("local copy equals persistent store copy:", oneQuote, readBackCopies.get(0));
    }

    //crUd
    @Test
    public void testUpdate_OneQuote() {

        //given
        CompositeKeyWithIdClassAnnotation preUpdateClone = CompositeKeyWithIdClassAnnotation.of(oneQuote);
        oneQuote.setNetWorth(1_500_000.45);
        List<CompositeKeyWithIdClassAnnotation> quotes = List.of(oneQuote);
        dao.create(List.of(oneQuote));

        //when
        dao.update(List.of(oneQuote));
        preUpdateClone.setNetWorth(preUpdateClone.getNetWorth() + 1);

        //then
        CompositeKeyWithIdClassAnnotation persistentCopy = dao.findByPrimaryKey(oneQuote);
        //go back to persistent story to update quotes
        dao.refresh(quotes);
        System.out.println("local pre-update clone: " + preUpdateClone);
        System.out.println("persistent copy:" + persistentCopy);
        assertEquals(oneQuote, persistentCopy);
        assertNotEquals(preUpdateClone, persistentCopy);

        tearDown = true;
    }

    //cruD
    @Test
    public void testDelete_OneQuote() {
        //given
        dao.create(List.of(oneQuote));
        assertEquals("after create, size of persistent store should be 1:", 1, dao.read().size());

        //when
        dao.delete(List.of(oneQuote));

        //then
        assertEquals("after delete, size of persistent store should be 0:", 0, dao.read().size());

    }
}
