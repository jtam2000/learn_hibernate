package com.github.jtam2000.stockquotes;

import com.github.jtam2000.jpa.JPA;
import com.github.jtam2000.jpa.JPADataAccessObject;

import javax.persistence.EntityManager;
import java.util.List;

public class StockQuoteDAO implements JPADataAccessObject<StockQuoteWithAnnotation> {

    private List<StockQuoteWithAnnotation> itemsToCreate;

    private String jpuName;

    public StockQuoteDAO(String jpuName) {
        this.jpuName=jpuName;
    }

    @Override
    public void create(List<StockQuoteWithAnnotation> items) {

        try (JPA jpa = new JPA(jpuName)) {
            itemsToCreate = items;
            jpa.commitTransaction(this::create);
        }

    }

    private void create(EntityManager entityManager) {

        itemsToCreate.forEach(entityManager::persist);
    }


    @Override
    public List<StockQuoteWithAnnotation> read() {
        
        try (JPA jpa = new JPA(jpuName)) {

            return readFromTable(jpa.getEntityManager(),StockQuoteWithAnnotation.class);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

        try (JPA jpa = new JPA(jpuName)) {
            jpa.commitTransaction((m)-> deleteFromTable(m, StockQuoteWithAnnotation.class));
        }
    }
}
