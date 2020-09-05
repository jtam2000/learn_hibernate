package com.github.jtam2000.package1;

import com.github.jtam2000.stockquotes.StockQuote;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class StartHibernate {

    public static void main(String[] args) {

        System.out.printf("%nHibernate Learning Program begins: => %s%n", LocalDateTime.now().
                format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));


        //Standard Registry
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

        //Metadata Sources
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        //Session Factory
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        //Session instance
        Session session = factory.openSession();

        //Begin TRAN
        Transaction t = session.beginTransaction();

        StockQuote sq = new StockQuote();
        sq.setTicker("IBM");
        sq.setAsk(123.45F);
        sq.setBid(124.98F);
        sq.setCurrency("USD");
        sq.setOutstanding_shares(7_000_000D);
        sq.setAvailable_shares(10_000_000D);
        sq.setQuote_timestamp(LocalDateTime.now());

        //Save value to DB
        session.save(sq);

        //END TRAN - commit the tranaction
        t.commit();

        System.out.println("successfully saved");

        //Clean up: close Factory & session, in reverse order makes sense
        session.close();
        factory.close();
    }
}
