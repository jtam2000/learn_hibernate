package com.github.jtam2000.hibernate;

import com.github.jtam2000.stockquotes.StockQuote;

import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.boot.registry.StandardServiceRegistryBuilder.DEFAULT_CFG_RESOURCE_NAME;

public class DemoStockQuoteInHibernateUsingConfiguration extends DemoStockQuoteInHibernateAbstract {

    public static final String TABLE_NAME = StockQuote.class.getSimpleName();

    public static void demoStockQuoteWithConfiguration() {

        DemoStockQuoteInHibernateUsingConfiguration demo = new DemoStockQuoteInHibernateUsingConfiguration();
        printToConsoleApplicationStartMessageWithLocalDateTime();
        demo.demoHibernateCrud(DEFAULT_CFG_RESOURCE_NAME);
    }

    public void saveOneStockQuoteWithHibernateInTransaction(Hibernate hbn) {

        hbn.commitTransaction(this::saveOneStockQuote);
        System.out.println("successfully saved");

    }

    public void saveOneStockQuote(Hibernate h) {

        StockQuote sq = getSingleStockQuote();
        persistToHibernate(h, sq);
    }

    //not intending to implement this as the new approach is to use annotation and not configuration
    protected void updateOneStockQuote(Hibernate hbn) {

        System.err.println("TODO: Not Implemented: Update function in Demo Stock quote using Configuration");
    }

    //not intending to implement this as the new approach is to use annotation and not configuration
    protected void deleteOneStockQuote(Hibernate hbn) {

        System.err.println("TODO: Not Implemented: Delete function in Demo Stock quote using Configuration");
    }

    @Override
    protected void showTableContent(Hibernate hbn) {
        readStockQuote(hbn);
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


     protected void readStockQuote(Hibernate hbn) {

        List<StockQuote> results = getFromTable(hbn);

         System.out.println("results from " + TABLE_NAME);
         System.out.println("row count: " + results.size());
         results.forEach(System.out::println);
    }

    private List<StockQuote> getFromTable(Hibernate hbn) {

        String jpql = "from " + TABLE_NAME;

        return hbn.getSessionInstance()
                .createQuery(jpql, StockQuote.class).list();
    }

    @Override
    public List<?> getTableContent(Hibernate hbn) {
        return getFromTable(hbn);
    }
}
