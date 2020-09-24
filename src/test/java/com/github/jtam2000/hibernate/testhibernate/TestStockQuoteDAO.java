package com.github.jtam2000.hibernate.testhibernate;

import com.github.jtam2000.stockquotes.StockQuoteDAO;
import com.github.jtam2000.stockquotes.StockQuoteWithAnnotation;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestStockQuoteDAO {

    private StockQuoteWithAnnotation oneQuote = new StockQuoteWithAnnotation();

    public static List<LocalDate> DIVIDEND_DATES =
            List.of(LocalDate.of(2020, 3, 29),
                    LocalDate.of(2020, 6, 29),
                    LocalDate.of(2020, 9, 29)
            );

    @Before
    public void setup() {

        oneQuote.setTicker("APPL");
        oneQuote.setAsk(123.45F);
        oneQuote.setBid(124.98F);
        oneQuote.setCurrency("USD");
        oneQuote.setOutstanding_shares(7_000_000D);
        oneQuote.setAvailable_shares(10_000_000D);
        oneQuote.setQuote_timestamp(LocalDateTime.now());
        oneQuote.setDividend_date(DIVIDEND_DATES);
    }


    @Test
    public void testCreate() {

        StockQuoteDAO dao = new StockQuoteDAO(oneQuote);
        List<StockQuoteWithAnnotation> createdStockQuote = dao.create();

        assertEquals(1, createdStockQuote.size());
        assertEquals(oneQuote, createdStockQuote.get(0));
    }


}
