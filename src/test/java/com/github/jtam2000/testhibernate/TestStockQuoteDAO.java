package com.github.jtam2000.testhibernate;

import com.github.jtam2000.jpa.JPA;
import com.github.jtam2000.stockquotes.StockQuoteDAO;
import com.github.jtam2000.stockquotes.StockQuoteWithAnnotation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestStockQuoteDAO {

    private final List<StockQuoteWithAnnotation> sampleStockQuotes = List.of(

            StockQuoteWithAnnotation.of("APPL", 123.45F, 124.98F, "USD", 7_000_000D, 10_000_000D, LocalDateTime.now(),
                    List.of(LocalDate.of(2020, 7, 3), LocalDate.of(2020, 5, 4), LocalDate.of(2020, 2, 8))),
            StockQuoteWithAnnotation.of("APPL", 123.88F, 124.93F, "USD", 6_090_000D, 10_000_000D, LocalDateTime.now(),
                    List.of(LocalDate.of(2020, 7, 3), LocalDate.of(2020, 5, 4), LocalDate.of(2020, 2, 8))),

            StockQuoteWithAnnotation.of("IBM", 145.01F, 147.26F, "USD", 8_000_000D, 14_000_000D, LocalDateTime.now(),
                    List.of(LocalDate.of(2020, 1, 4), LocalDate.of(2020, 4, 15))
            )
    );

    private StockQuoteWithAnnotation oneQuote;

    //can use another jpu to have less generated sql output
    final private String jpuName = "jpu_verbose_1";

    private final JPA jpa = new JPA(jpuName);

    StockQuoteDAO dao;

    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
    private boolean tearDown = true;

    @Before
    public void setup() {

        dao = new StockQuoteDAO(jpa);

        oneQuote = sampleStockQuotes.get(0);
        removeAllRows();
        assertEquals("Set up table should be zero:", 0, dao.read().size());
    }

    @Test
    public void testCreate_MultipleQuote() {

        createMultipleQuote();

        List<StockQuoteWithAnnotation> listInDB = dao.read();
        assertEquals("row count:", sampleStockQuotes.size(), listInDB.size());
        assertArrayEquals("Assert created content:", sampleStockQuotes.toArray(), listInDB.toArray());
    }

    @Test
    public void testCreate_OneQuote() {

        createOneQuote();

        List<StockQuoteWithAnnotation> listInDB = dao.read();
        assertEquals("row count:", 1, listInDB.size());
        assertEquals("row content:", oneQuote, listInDB.get(0));

    }

    private void createOneQuote() {

        dao.create(List.of(oneQuote));
    }

    private void createMultipleQuote() {

        dao.create(sampleStockQuotes);
    }

    @After
    public void tearDown() {

        if (tearDown) {
            removeAllRows();
            assertEquals("Tear down table should be zero:",
                    0,
                    dao.read().size());
        }

        jpa.close();
    }


    private void removeAllRows() {

        dao.delete();
    }

    @Test
    public void testRead_OneQuote() {

        createOneQuote();

        List<StockQuoteWithAnnotation> result = dao.read();
        assertEquals("read row count:", 1, result.size());
        assertEquals("read row content:", oneQuote, result.get(0));

    }

    @Test
    public void testDelete_AllQuotes() {

        createOneQuote();
        assertDelete("post delete 1 quote, size:");
    }

    @Test
    public void testDelete_OneQuote() {

        //given
        createMultipleQuote();

        //when
        StockQuoteWithAnnotation quoteToDelete = sampleStockQuotes.get(1);
        dao.delete(quoteToDelete);

        //then
        assertNull("should NOT find the deleted item:", dao.findByPrimaryKey(quoteToDelete));
        assertItemExistsInDb(sampleStockQuotes.get(0), sampleStockQuotes.get(2));
        assertEquals("size after delete: ", 2, dao.read().size());
    }

    private void assertItemExistsInDb(StockQuoteWithAnnotation... items) {

        for (StockQuoteWithAnnotation item : items)
            assertEquals("Item in Db:", item, dao.findByPrimaryKey(item));
    }

    @Test
    public void testDelete_MultipleQuote() {

        createMultipleQuote();
        assertDelete("post delete multiple quotes, size:");
    }

    private void assertDelete(String testMessage) {

        dao.delete();
        assertEquals(testMessage, 0, dao.read().size());
    }


    @Test
    public void testUpdate_OneQuote_UpdateOneAttribute() {

        //Given
        StockQuoteWithAnnotation updatedLocally = getOneItemFromSingleItemTable();
        StockQuoteWithAnnotation preUpdate = StockQuoteWithAnnotation.of(updatedLocally);

        updatedLocally.setAsk(101.02F);

        //When
        dao.update(List.of(updatedLocally));

        //Then
        StockQuoteWithAnnotation postUpdate = dao.findByPrimaryKey(updatedLocally);
        assertEquals(updatedLocally, postUpdate);

        System.out.println("before update: " + preUpdate);
        System.out.println("After update: " + postUpdate);

    }


    @Test
    public void testUpdate_OneQuoteUpdate_MultipleAttributes() {

        //Given
        StockQuoteWithAnnotation updatedLocally = getOneItemFromSingleItemTable();
        StockQuoteWithAnnotation preUpdate = StockQuoteWithAnnotation.of(updatedLocally);

        updatedLocally.setAsk(222.22F);
        updatedLocally.setTicker("IBM");
        updatedLocally.setDividend_date(List.of(LocalDate.of(2020, 6, 2), LocalDate.of(2020, 4, 3), LocalDate.of(2020, 1, 7)));


        //When
        dao.update(List.of(updatedLocally));

        //Then
        StockQuoteWithAnnotation postUpdate = dao.findByPrimaryKey(updatedLocally);

        assertEquals(updatedLocally, postUpdate);

        System.out.println("before update: " + preUpdate);
        System.out.println("After update: " + postUpdate);
    }


    @Test
    public void testUpdate_OneQuoteUpdate_UpdateSubTableAttributes() {

        //Given
        StockQuoteWithAnnotation updatedLocally = getOneItemFromSingleItemTable();
        StockQuoteWithAnnotation preUpdate = StockQuoteWithAnnotation.of(updatedLocally);

        List<LocalDate> dividendDate = new ArrayList<>(updatedLocally.getDividend_date());
        dividendDate.add(LocalDate.of(2020, 12, 31));
        updatedLocally.setDividend_date(dividendDate);


        //When
        dao.update(List.of(updatedLocally));

        //Then
        StockQuoteWithAnnotation postUpdate = dao.findByPrimaryKey(updatedLocally);

        assertEquals(updatedLocally, postUpdate);

        System.out.println("before update: " + preUpdate);
        System.out.println("After update: " + postUpdate);
        assertEquals("row count after update", 1, dao.read().size());
    }


    private StockQuoteWithAnnotation getOneItemFromSingleItemTable() {

        //create a stock quote, setup will already removed all existing entries
        createOneQuote();
        List<StockQuoteWithAnnotation> preUpdateQuotes = dao.read();
        return preUpdateQuotes.get(0);
    }
}
