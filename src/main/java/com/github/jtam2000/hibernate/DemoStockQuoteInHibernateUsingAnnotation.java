package com.github.jtam2000.hibernate;

import com.github.jtam2000.stockquotes.StockQuoteWithAnnotation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.github.jtam2000.hibernate.Hibernate.DEFAULT_CFG_ANNOTATION;

public class DemoStockQuoteInHibernateUsingAnnotation extends DemoStockQuoteInHibernateAbstract {

    public static void demoStockQuoteWithAnnotations() {

        printToConsoleApplicationStartMessageWithLocalDateTime();

        DemoStockQuoteInHibernateUsingAnnotation demo = new DemoStockQuoteInHibernateUsingAnnotation();
        demo.demoHibernateActivities(DEFAULT_CFG_ANNOTATION);
    }


    @Override
    public void saveOneStockQuote(Hibernate h) {
        StockQuoteWithAnnotation sq = getSingleStockQuote();
        persistToHibernate(h, sq);
    }

    protected void updateOneStockQuote(Hibernate hbn) {

        final int updateAddValue = -250;
        StockQuoteWithAnnotation oneRow = getFirstRow(hbn);

        oneRow.setChangedFields(2);
        oneRow.setOutstanding_shares(oneRow.getOutstanding_shares() + updateAddValue);
        System.out.println("new update value is :" + oneRow);

        hbn.getSessionInstance().update(oneRow);

    }

    protected void deleteOneStockQuote(Hibernate hbn) {

        StockQuoteWithAnnotation oneRow = getLastRow(hbn);
        System.out.println("Deleted: => " + oneRow);
        hbn.getSessionInstance().delete(oneRow);
    }

    private StockQuoteWithAnnotation getFirstRow(Hibernate hbn) {
        List<StockQuoteWithAnnotation> tableValues = getFromTable(hbn);
        return  tableValues.get(0);
    }

    private StockQuoteWithAnnotation getLastRow(Hibernate hbn) {
        List<StockQuoteWithAnnotation> tableValues = getFromTable(hbn);
        return  tableValues.get(tableValues.size()-1);
    }
    private List<StockQuoteWithAnnotation> getFromTable(Hibernate hbn) {

        return (List<StockQuoteWithAnnotation>) hbn.
                getSessionInstance()
                .createQuery("from StockQuoteWithAnnotation").list();
    }

    protected void readStockQuote(Hibernate hbn) {

        List<StockQuoteWithAnnotation> results = getFromTable(hbn);

        System.out.println("results: from StockQuoteWithAnnotation");
        int rowCounter = 0;
        for (StockQuoteWithAnnotation sq : results) {
            System.out.println(sq);
            rowCounter++;
        }
        System.out.println("row count: " + rowCounter);

    }


    private static StockQuoteWithAnnotation getSingleStockQuote() {

        StockQuoteWithAnnotation sq = new StockQuoteWithAnnotation();

        sq.setTicker("APPL");
        sq.setAsk(123.45F);
        sq.setBid(124.98F);
        sq.setCurrency("USD");
        sq.setOutstanding_shares(7_000_000D);
        sq.setAvailable_shares(10_000_000D);
        sq.setQuote_timestamp(LocalDateTime.now());
        sq.setDividend_date(List.of(LocalDate.of(2020, 2, 24), LocalDate.of(2010, 6, 25)));
        return sq;
    }

    void persistToHibernate(Hibernate h, StockQuoteWithAnnotation sq) {

        System.out.printf("Trying to persist: %s%n", sq);
        h.getSessionInstance().persist(sq);
        h.getSessionInstance().flush();

    }
}

