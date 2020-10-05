package com.github.jtam2000.testhibernate;

import com.github.jtam2000.jpa.JPA;
import com.github.jtam2000.jpa.primarykey.SinglePrimaryKey;
import com.github.jtam2000.jpa.primarykey.SinglePrimaryKeyDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class TestSinglePrimaryKey {

    final private String jpuName = "jpu_single_primarykey";

    private final JPA jpa = new JPA(jpuName);

    private final SinglePrimaryKeyDao dao = new SinglePrimaryKeyDao(jpa);

    private SinglePrimaryKey testItem;
    private SinglePrimaryKey actualItem;

    @Before
    public void setUp() {
        dao.delete();
    }
    @After
    public void tearDown() {
        dao.delete();
        jpa.close();
    }

    //Crud
    @Test
    public void test_createSingleEntity() {

        testItem = saveOneItem();
        assertItemCreated();
    }

    //cRud
    @Test
    public void test_readSingleEntity() {
        testItem = saveOneItem();
        SinglePrimaryKey item = dao.read().get(0);
        assertEquals("read item from db :", item, dao.findByPrimaryKey(item));
    }

    //crUd
    @Test
    public void test_updateSingleItem(){

        testItem = saveOneItem();
        testItem.setDoubleValue(1_000_000_000.34D);

        dao.update(List.of(testItem));
        SinglePrimaryKey updateItem=assertOneRow();
        assertEquals(testItem, updateItem);

    }

    //cruD
    @Test
    public void test_DeleteSingleEntity() {

        testItem = saveOneItem();
        SinglePrimaryKey item = dao.read().get(0);
        dao.delete(List.of(item));

        assertEquals("zero item after delete single item", 0,dao.read().size() );
        assertNotNull("item in memory still not null", item);

    }


    private SinglePrimaryKey assertOneRow() {

        List<SinglePrimaryKey> tableData = dao.read();
        assertEquals("only 1 row exists in table:", 1, tableData.size());
        return tableData.get(0);

    }

    private void assertItemCreated() {

        SinglePrimaryKey itemCreated = assertOneRow();
        assertTrue("item created should match what is meant to be created => ",
                testItem.valuesEqual(itemCreated));
    }

    private SinglePrimaryKey saveOneItem() {

        testItem = SinglePrimaryKey.randomInstance();
        System.out.println("item created for insertion => " + testItem);
        dao.create(List.of(testItem));
        return testItem;

    }


}