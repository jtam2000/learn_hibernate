package com.github.jtam2000.hibernate;

import com.github.jtam2000.stockquotes.StockQuote;
import com.github.jtam2000.stockquotes.StockQuoteWithAnnotation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public abstract class DemoStockQuoteInHibernateAbstract {


    protected static void printToConsoleApplicationStartMessageWithLocalDateTime() {

        System.out.printf("%nHibernate Learning Program begins: => %s%n", LocalDateTime.now().
                format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
    }

    public void demoHibernateCrud(String hbConfigFile) {

        try (Hibernate hbn = Hibernate.getInstance(hbConfigFile)) {
            //Crud
            saveOneStockQuoteWithHibernateInTransaction(hbn);
            //cRud
            readStockQuotesInTransaction(hbn);
            //crUd
            updateStockQuotesInTransaction(hbn);
            //cruD
            deleteStockQuoteInTransaction(hbn);
        }
    }

    public void displayTableContent(String hbConfigFile){

        try (Hibernate hbn = Hibernate.getInstance(hbConfigFile)) {
            hbn.commitTransaction(this::showTableContent);
        }
    }

    protected void updateStockQuotesInTransaction(Hibernate hbn) {


        hbn.commitTransaction(this::updateOneStockQuote);
        System.out.println("successfully updated");
    }

    protected void deleteStockQuoteInTransaction(Hibernate hbn) {


        hbn.commitTransaction(this::deleteOneStockQuote);
        System.out.println("successfully deleted");
    }

    protected void saveOneStockQuoteWithHibernateInTransaction(Hibernate hbn) {

        hbn.commitTransaction(this::saveOneStockQuote);
        System.out.println("successfully saved");
    }

    protected void readStockQuotesInTransaction(Hibernate hbn) {

        hbn.commitTransaction(this::readStockQuote);
        System.out.println("successfully read");
    }

    protected void showStockQuotesTableContent(Hibernate hbn) {

        hbn.commitTransaction(this::showTableContent);
    }


    protected abstract void saveOneStockQuote(Hibernate hbn);
    protected abstract void readStockQuote(Hibernate hbn);
    protected abstract void updateOneStockQuote(Hibernate hbn);
    protected abstract void deleteOneStockQuote(Hibernate hbn);
    protected abstract void showTableContent(Hibernate hbn);
    protected abstract List<?> getTableContent(Hibernate hbn);
}
