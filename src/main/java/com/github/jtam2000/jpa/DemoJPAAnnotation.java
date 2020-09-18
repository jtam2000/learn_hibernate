package com.github.jtam2000.jpa;


import com.github.jtam2000.hibernate.DemoStockQuoteInHibernateUsingAnnotation;
import com.github.jtam2000.stockquotes.StockQuoteWithAnnotation;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DemoJPAAnnotation {

    private final static String JPU_NAME = "jpu_1";
    private final static String JPU_NAME_VERBOSE = "jpu_verbose_1";

    private StockQuoteWithAnnotation testItem;

    public List<StockQuoteWithAnnotation> create(StockQuoteWithAnnotation item) throws Exception {

        testItem = item;
        List<StockQuoteWithAnnotation> result=null;

        try (JPA jpa = new JPA(JPU_NAME_VERBOSE)) {
            jpa.commitTransaction(this::createStockQuote);
            result = getStockQuote(jpa.getEntityManager());
        }
        return result;
    }

    private void createStockQuote(EntityManager em) {

        em.persist(testItem);
    }

    private List<StockQuoteWithAnnotation> getStockQuote(EntityManager em) {

        String query="from " + StockQuoteWithAnnotation.class.getSimpleName();
        TypedQuery<StockQuoteWithAnnotation> q =
                em.createQuery(query, StockQuoteWithAnnotation.class);
        return q.getResultList();
    }
}
