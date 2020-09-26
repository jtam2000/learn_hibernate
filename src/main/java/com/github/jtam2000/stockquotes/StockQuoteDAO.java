package com.github.jtam2000.stockquotes;

import com.github.jtam2000.jpa.JPA;
import com.github.jtam2000.jpa.JPADataAccessObject;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
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
            getID(jpa.getEntityManager(), StockQuoteWithAnnotation.class);
            return readFromTable(jpa.getEntityManager(),StockQuoteWithAnnotation.class);
        }
    }

    @Override
    public void update(List<StockQuoteWithAnnotation> updateItems) {

        try (JPA jpa = new JPA(jpuName)) {

            CriteriaBuilder cb = jpa.getEntityManager().getCriteriaBuilder();

            CriteriaUpdate<StockQuoteWithAnnotation> update = cb.createCriteriaUpdate(StockQuoteWithAnnotation.class);

            //metadata model
            Root<StockQuoteWithAnnotation> root = update.from(StockQuoteWithAnnotation.class);

            Path<Object> criteria = root.get("quote_timestamp");

            LocalDateTime criteriaValue = updateItems.get(0).getQuote_timestamp();

            Predicate whereClause = cb.equal(criteria, criteriaValue);
            update.where(whereClause);

            System.out.println("update query is " + update);

            createUpdateFilter(cb,root, updateItems.get(0));

           // updateTable(jpa.getEntityManager(), StockQuoteWithAnnotation.class,);
        }
    }

    private void createUpdateFilter(CriteriaBuilder cb,
                                    Root<StockQuoteWithAnnotation> metaModel,
                                    StockQuoteWithAnnotation updateItem ) {

        Class<? extends StockQuoteWithAnnotation> inputClass = updateItem.getClass();

        Path<Object> expression = metaModel.get("quote_timestamp");

        Expression<Boolean> filterPredicate = cb.equal(expression,
                updateItem.getQuote_timestamp());

        //return filterPredicate;
    }

    @Override
    public void delete() {

        try (JPA jpa = new JPA(jpuName)) {
            jpa.commitTransaction((m)-> deleteFromTable(m, StockQuoteWithAnnotation.class));
        }
    }

    public StockQuoteWithAnnotation find(StockQuoteWithAnnotation updatedLocally) {
        return new StockQuoteWithAnnotation();
    }
}
