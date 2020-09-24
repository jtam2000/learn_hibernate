package com.github.jtam2000.hibernate.testhibernate;

import com.github.jtam2000.stockquotes.StockQuote;
import com.github.jtam2000.stockquotes.StockQuoteDAO;
import com.github.jtam2000.stockquotes.StockQuoteWithAnnotation;
import org.apache.kafka.clients.Metadata;
import org.hibernate.Metamodel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestStockQuoteDAO {

    private List<StockQuoteWithAnnotation> sampleStockQuotes = List.of(

            StockQuoteWithAnnotation.of("APPL", 123.45F, 124.98F, "USD", 7_000_000D, 10_000_000D, LocalDateTime.now(),
                    List.of(LocalDate.of(2020, 7, 3), LocalDate.of(2020, 5, 4), LocalDate.of(2020, 2, 8))),
            StockQuoteWithAnnotation.of("APPL", 123.88F, 124.93F, "USD", 6_090_000D, 10_000_000D, LocalDateTime.now(),
                    List.of(LocalDate.of(2020, 7, 3), LocalDate.of(2020, 5, 4), LocalDate.of(2020, 2, 8))),

            StockQuoteWithAnnotation.of("IBM", 145.01F, 147.26F, "USD", 8_000_000D, 14_000_000D, LocalDateTime.now(),
                    List.of(LocalDate.of(2020, 1, 4), LocalDate.of(2020, 4, 15))
            )
    );

    private StockQuoteWithAnnotation oneQuote;
    private Metamodel mm;

    final private String jpuName = "jpu_verbose_1";

    StockQuoteDAO dao;

    @Before
    public void setup() {
        dao = new StockQuoteDAO(jpuName);


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

        removeAllRows();
        assertEquals("Tear down table should be zero:",
                0,
                dao.read().size());

    }


    private void removeAllRows() {

        dao.delete();
    }

    @Test
    public void testRead() {

        createOneQuote();

        List<StockQuoteWithAnnotation> result = dao.read();
        assertEquals("read row count:", 1, result.size());
        assertEquals("read row content:", oneQuote, result.get(0));

    }

    @Test
    public void testDelete_OneQuote() {

        createOneQuote();
        assertDelete("post delete 1 quote, size:");
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
    public void testUpdate_OneQuote() {

        assertFalse(true);
    }
}
