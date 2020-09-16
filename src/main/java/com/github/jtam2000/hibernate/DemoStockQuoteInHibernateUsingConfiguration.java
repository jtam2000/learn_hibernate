package com.github.jtam2000.hibernate;

import com.github.jtam2000.stockquotes.StockQuote;

import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.boot.registry.StandardServiceRegistryBuilder.DEFAULT_CFG_RESOURCE_NAME;

public class DemoStockQuoteInHibernateUsingConfiguration extends DemoStockQuoteInHibernateAbstract {

    public static void demoStockQuoteWithConfiguration() {
        DemoStockQuoteInHibernateUsingConfiguration demo = new DemoStockQuoteInHibernateUsingConfiguration();
        printToConsoleApplicationStartMessageWithLocalDateTime();
        demo.demoHibernateActivities(DEFAULT_CFG_RESOURCE_NAME);
    }

    public void saveOneStockQuoteWithHibernateInTransaction(Hibernate hbn) {

        hbn.commitTransaction(this::saveOneStockQuote);
        System.out.println("successfully saved");

    }

    public void saveOneStockQuote(Hibernate h) {

        StockQuote sq = getSingleStockQuote();
        persistToHibernate(h, sq);
    }

    protected void updateOneStockQuote(Hibernate hbn) {
        System.err.println("TODO: Not Implemented: Update function in Demo Stock quote using Configuration");
    }
    protected void deleteOneStockQuote(Hibernate hbn) {
        System.err.println("TODO: Not Implemented: Delete function in Demo Stock quote using Configuration");
    }
    void persistToHibernate(Hibernate h, StockQuote sq) {

        System.out.printf("Trying to persist: %s%n", sq);
        h.getSessionInstance().persist(sq);
        h.getSessionInstance().flush();
    }

     static StockQuote getSingleStockQuote() {

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


     protected void readStockQuote(Hibernate hibernate) {

        List<StockQuote> results = (List<StockQuote>) hibernate.
                getSessionInstance()
                .createQuery("from StockQuote").list();

        System.out.println("results: from StockQuote with Configuration");
        int rowCounter = 0;
        for (StockQuote sq : results) {
            System.out.println(sq);
            rowCounter++;
        }
        System.out.println("row count: " + rowCounter);
    }
}
