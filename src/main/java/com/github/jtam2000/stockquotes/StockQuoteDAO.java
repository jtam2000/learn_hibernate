package com.github.jtam2000.stockquotes;

import com.github.jtam2000.jpa.JPA;
import com.github.jtam2000.jpa.JPADataAccessObject;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StockQuoteDAO implements JPADataAccessObject<StockQuoteWithAnnotation> {

    private static final String PRIMARY_KEY = StockQuoteWithAnnotation_.QUOTE_TIMESTAMP;

    private static final Class<StockQuoteWithAnnotation> targetClass = StockQuoteWithAnnotation.class;


    private List<StockQuoteWithAnnotation> itemsToCreate;

    private JPA jpa;
    private CriteriaBuilder cb;

    public StockQuoteDAO(JPA jpa) {
        this.jpa = jpa;
        cb = jpa.getEntityManager().getCriteriaBuilder();
    }

    @Override
    public void create(List<StockQuoteWithAnnotation> items) {

        itemsToCreate = items;
        jpa.commitTransaction(this::create);

    }

    private void create(EntityManager entityManager) {

        itemsToCreate.forEach(entityManager::persist);
    }


    @Override
    public List<StockQuoteWithAnnotation> read() {

        return readFromTable(jpa.getEntityManager(), targetClass);
    }

    @Override
    public List<StockQuoteWithAnnotation> readByPrimaryKey(List<StockQuoteWithAnnotation> pks) {

        ArrayList<StockQuoteWithAnnotation> rtn = new ArrayList<>();
        pks.forEach(i -> rtn.add(jpa.getEntityManager().find(targetClass, i)));
        return rtn;
    }

    @Override
    public void update(List<StockQuoteWithAnnotation> updateItems) {

        updateItems.forEach(i->jpa.commitTransaction(m-> System.out.println(i)));
    }

    @Override
    public void delete() {

        jpa.commitTransaction((m) -> deleteFromTable(m, targetClass));
    }

    @Override
    public void delete(List<StockQuoteWithAnnotation> items) {
        items.forEach(x -> delete(x));
    }


    public void delete(StockQuoteWithAnnotation deleteItem) {

        jpa.commitTransaction((m) -> m.remove(deleteItem));
    }

    @Override
    public String primaryKeyName() {

        return StockQuoteWithAnnotation_.QUOTE_TIMESTAMP;
    }

    public List<StockQuoteWithAnnotation> find(CriteriaQuery<StockQuoteWithAnnotation> criteriaQuery) {

        return readFromTable(jpa.getEntityManager(), criteriaQuery);

    }

    public StockQuoteWithAnnotation findByPrimaryKey(StockQuoteWithAnnotation item) {

        return jpa.getEntityManager().find(targetClass, item.getQuote_timestamp());

    }


    public CriteriaQuery<StockQuoteWithAnnotation> createQueryOnPrimaryKey(StockQuoteWithAnnotation primaryKeyValue) {


        CriteriaQuery<StockQuoteWithAnnotation> criteriaQuery = cb.createQuery(targetClass);
        Path<LocalDateTime> primaryKey = criteriaQuery.from(targetClass).get(primaryKeyName());

        LocalDateTime pkValue = primaryKeyValue.getQuote_timestamp();
        criteriaQuery.where(cb.equal(primaryKey, pkValue));
        return criteriaQuery;
    }

}
