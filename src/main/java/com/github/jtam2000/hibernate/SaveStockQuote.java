package com.github.jtam2000.hibernate;

import com.github.jtam2000.stockquotes.StockQuote;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class SaveStockQuote {

    public static void main(String[] args) throws Exception {

        System.out.printf("%nHibernate Learning Program begins: => %s%n", LocalDateTime.now().
                format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

        try(Hibernate hbn = Hibernate.getInstance()) {

            hbn.commitTransaction(SaveStockQuote::save);
            System.out.println("successfully saved");
        }
    }

    static public void save(Hibernate h) {

        StockQuote sq = new StockQuote();

        sq.setTicker("IBM");
        sq.setAsk(123.45F);
        sq.setBid(124.98F);
        sq.setCurrency("USD");
        sq.setOutstanding_shares(7_000_000D);
        sq.setAvailable_shares(10_000_000D);
        sq.setQuote_timestamp(LocalDateTime.now());

        //Save value to DB
        h.getSessionInstance().persist(sq);
        h.getSessionInstance().flush();
    }
}
