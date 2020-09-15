package com.github.jtam2000.hibernate;

import com.github.jtam2000.stockquotes.StockQuote;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class SaveStockQuote {

    public static void main(String[] args) throws Exception {

        printToConsoleApplicationStartMessageWithLocalDateTime();

        tryToSaveOneStockQuoteWithHibernateInTransaction();
    }

    private static void tryToSaveOneStockQuoteWithHibernateInTransaction() throws Exception {

        try(Hibernate hbn = Hibernate.getInstance()) {

            hbn.commitTransaction(SaveStockQuote::saveOneStockQuote);
            System.out.println("successfully saved");
        }
    }

    private static void printToConsoleApplicationStartMessageWithLocalDateTime() {

        System.out.printf("%nHibernate Learning Program begins: => %s%n", LocalDateTime.now().
                format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
    }

    static public void saveOneStockQuote(Hibernate h) {

        StockQuote sq = getSingleStockQuote();
        persistToHibernate(h, sq);
    }

    private static void persistToHibernate(Hibernate h, StockQuote sq) {

        h.getSessionInstance().persist(sq);
        h.getSessionInstance().flush();
    }

    private static StockQuote getSingleStockQuote() {

        StockQuote sq = new StockQuote();

        sq.setTicker("IBM");
        sq.setAsk(123.45F);
        sq.setBid(124.98F);
        sq.setCurrency("USD");
        sq.setOutstanding_shares(7_000_000D);
        sq.setAvailable_shares(10_000_000D);
        sq.setQuote_timestamp(LocalDateTime.now());
        return sq;
    }
}
