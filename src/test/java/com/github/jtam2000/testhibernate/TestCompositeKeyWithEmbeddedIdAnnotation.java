package com.github.jtam2000.testhibernate;

import com.github.jtam2000.jpa.compositeprimarykey.CompositeKeyWithEmbeddedIdAnnotation;
import com.github.jtam2000.jpa.dao.JPA;
import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class TestCompositeKeyWithEmbeddedIdAnnotation {

    final private String jpuName = "jpu_composite_primarykey_embedded_id";

    private final JPA jpa = new JPA(jpuName);

    private JPADataAccessDaoImpl<CompositeKeyWithEmbeddedIdAnnotation> dao;
    private final Class<CompositeKeyWithEmbeddedIdAnnotation> targetClass = CompositeKeyWithEmbeddedIdAnnotation.class;

    private CompositeKeyWithEmbeddedIdAnnotation oneQuote;

    private boolean tearDown = true;

    @Before
    public void setup() {

        dao = new JPADataAccessDaoImpl<>(jpa, targetClass);

        //blank table before every test
        dao.delete();

        //create one example
        oneQuote = CompositeKeyWithEmbeddedIdAnnotation.of();
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

        List<CompositeKeyWithEmbeddedIdAnnotation> listInDB = dao.read();
        assertEquals("row count:", 1, listInDB.size());
        assertEquals("row content:", oneQuote, listInDB.get(0));

    }

    //cRud
    @Test
    public void testRead_OneQuote() {

        //given
        List<CompositeKeyWithEmbeddedIdAnnotation> quotes = List.of(oneQuote);
        oneQuote.setNetWorth(200_000_000);
        dao.create(quotes);

        List<CompositeKeyWithEmbeddedIdAnnotation> readBackCopies = dao.readByPrimaryKey(quotes);

        assertEquals("size of persistent story is 1:", 1, readBackCopies.size());
        assertEquals("local copy equals persistent store copy:", oneQuote, readBackCopies.get(0));

        tearDown = true;
    }

    //crUd
    @Test
    public void testUpdate_OneQuote() {

        //given
        CompositeKeyWithEmbeddedIdAnnotation preUpdateClone = CompositeKeyWithEmbeddedIdAnnotation.of(oneQuote);
        oneQuote.setNetWorth(1_500_000.45);
        List<CompositeKeyWithEmbeddedIdAnnotation> quotes = List.of(oneQuote);
        dao.create(List.of(oneQuote));

        //when
        dao.update(List.of(oneQuote));
        preUpdateClone.setNetWorth(preUpdateClone.getNetWorth() + 1);

        //then
        CompositeKeyWithEmbeddedIdAnnotation persistentCopy = dao.findByPrimaryKey(oneQuote);
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

    @Test
    public void testCreate_LongListOfQuotes() {

        //given
        final int quotesToCreate = 200;

        //when
        List<CompositeKeyWithEmbeddedIdAnnotation> quoteList = IntStream.rangeClosed(1, quotesToCreate)
                .mapToObj(i -> CompositeKeyWithEmbeddedIdAnnotation.of())
                .collect(Collectors.toList());
        dao.create(quoteList);

        //then
        List<CompositeKeyWithEmbeddedIdAnnotation> listInPersistentStore = dao.read();

        //local set to zero
        listInPersistentStore.forEach(q -> q.setNetWorth(0));

        //refresh from persistent store
        dao.refresh(listInPersistentStore);
        assertEquals("count of quotes created should  be:", quotesToCreate, listInPersistentStore.size());

        quoteList.forEach(q->
                assertEquals("persistent store should contain this quote:",
                        q, listInPersistentStore.get(listInPersistentStore.indexOf(q))));

        listInPersistentStore.forEach(q->
                assertEquals("persistent store should contain this quote:",
                        q, quoteList.get(quoteList.indexOf(q))));

        tearDown = false;
    }
}
