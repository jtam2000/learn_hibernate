package com.github.jtam2000.hibernate;

import com.github.jtam2000.stockquotes.StockQuoteWithAnnotation;
import org.hibernate.Transaction;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.github.jtam2000.hibernate.Hibernate.ANNOTATION_CONFIG;


public class DemoStockQuoteInHibernateUsingAnnotation extends DemoStockQuoteInHibernateAbstract {

    public static final String TABLE_NAME = StockQuoteWithAnnotation.class.getSimpleName();

    public static List<LocalDate> DIVIDEND_DATES =
            List.of(LocalDate.of(2020, 2, 24),
                    LocalDate.of(2010, 6, 25));

    public static void demoStockQuoteWithAnnotations() {

        printToConsoleApplicationStartMessageWithLocalDateTime();

        DemoStockQuoteInHibernateUsingAnnotation demo = new DemoStockQuoteInHibernateUsingAnnotation();
        demo.demoHibernateCrud(ANNOTATION_CONFIG);
    }


    @Override
    public void saveOneStockQuote(Hibernate h) {

        StockQuoteWithAnnotation sq = getSingleStockQuote();
        persistToHibernate(h, sq);
    }

    public void saveOneStockQuote(Hibernate h, Clock clock) {

        StockQuoteWithAnnotation sq = getSingleStockQuote(clock);
        persistToHibernate(h, sq);
    }

    private void setSessionAttributeToCreateDrop(Hibernate h) {

        final String hibernateHbm2ddl = "hbm2ddl.auto";
        final String CREATE_DROP = "create-drop";
        h.setFactoryProperties(hibernateHbm2ddl, CREATE_DROP);
        h.setSessionProperties(hibernateHbm2ddl, CREATE_DROP);
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
        return tableValues.get(0);
    }

    private StockQuoteWithAnnotation getLastRow(Hibernate hbn) {

        List<StockQuoteWithAnnotation> tableValues = getFromTable(hbn);
        return tableValues.get(tableValues.size() - 1);
    }

    private List<StockQuoteWithAnnotation> getFromTable(Hibernate hbn) {

        String jpql = "from " + TABLE_NAME;

        return hbn.getSessionInstance()
                .createQuery(jpql, StockQuoteWithAnnotation.class).list();
    }

    @Override
    protected void showTableContent(Hibernate hbn) {

        readStockQuote(hbn);
    }

    @Override
    public List<?> getTableContent(Hibernate hbn) {

        return getFromTable(hbn);
    }

    protected void readStockQuote(Hibernate hbn) {

        List<StockQuoteWithAnnotation> results = getFromTable(hbn);

        System.out.println("results from " + TABLE_NAME);
        System.out.println("row count: " + results.size());
        results.forEach(System.out::println);

    }


    public static StockQuoteWithAnnotation getSingleStockQuote(Clock clock) {

        StockQuoteWithAnnotation quote = getSingleStockQuote();
        quote.setQuote_timestamp(LocalDateTime.now(clock));
        return quote;
    }

    public static StockQuoteWithAnnotation getSingleStockQuote() {

        StockQuoteWithAnnotation sq = new StockQuoteWithAnnotation();

        sq.setTicker("APPL");
        sq.setAsk(123.45F);
        sq.setBid(124.98F);
        sq.setCurrency("USD");
        sq.setOutstanding_shares(7_000_000D);
        sq.setAvailable_shares(10_000_000D);
        sq.setQuote_timestamp(LocalDateTime.now());

        sq.setDividend_date(DIVIDEND_DATES);
        return sq;
    }

    void persistToHibernate(Hibernate h, StockQuoteWithAnnotation sq) {

        System.out.printf("Trying to persist: %s%n", sq);
        h.getSessionInstance().persist(sq);
        h.getSessionInstance().flush();

    }

    public void saveOneStockQuote(Clock clock) {

        try (Hibernate hbn = Hibernate.getInstance(ANNOTATION_CONFIG)) {

            setSessionAttributeToCreateDrop(hbn);

            Transaction t = hbn.getSessionInstance().beginTransaction();
            saveOneStockQuote(hbn,clock);
            t.commit();
        }
    }

    public List<StockQuoteWithAnnotation> getTableContent() {

        try (Hibernate hbn = Hibernate.getInstance(ANNOTATION_CONFIG)) {
            return getFromTable(hbn);
        }

    }
}

