package com.github.jtam2000.testhibernate;

import com.github.jtam2000.jpa.DemoJPAAnnotation;
import com.github.jtam2000.stockquotes.StockQuoteWithAnnotation;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestJPA {

    private static final List<LocalDate> DIVIDEND_DATES =
            List.of(LocalDate.of(2020, 3, 27),
                    LocalDate.of(2007, 4, 1));

    //TEST CRUD functionalities  - Create
    @Test
    public void testJPA_create() throws Exception {

        DemoJPAAnnotation demo = new DemoJPAAnnotation();

        StockQuoteWithAnnotation testItem = getSingleStockQuote();
        List<StockQuoteWithAnnotation> result = demo.create(testItem);
        assertEquals(1, result.size());
        assertEquals(testItem, result.get(0));

        System.out.println("item created in table:");
        result.forEach(System.out::println);


    }
    private static StockQuoteWithAnnotation getSingleStockQuote() {

        StockQuoteWithAnnotation sq = new StockQuoteWithAnnotation();

        sq.setTicker("IBM");
        sq.setAsk(100.45F);
        sq.setBid(101.98F);
        sq.setCurrency("USD");
        sq.setOutstanding_shares(7_200_000D);
        sq.setAvailable_shares(7_100_000D);
        sq.setQuote_timestamp(LocalDateTime.now());

        sq.setDividend_date(DIVIDEND_DATES);
        return sq;
    }
}

