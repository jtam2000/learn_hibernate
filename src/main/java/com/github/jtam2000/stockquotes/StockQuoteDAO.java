package com.github.jtam2000.stockquotes;

import com.github.jtam2000.jpa.JPA;
import com.github.jtam2000.jpa.JPADataAccessObject;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.time.LocalDateTime;
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
    public void update(List<StockQuoteWithAnnotation> updateItems) {

        CriteriaUpdate<StockQuoteWithAnnotation> criteriaUpdate = cb.createCriteriaUpdate(targetClass);
        Root<StockQuoteWithAnnotation> model = criteriaUpdate.from(targetClass);

        EntityType<StockQuoteWithAnnotation> x = jpa.getEntityManager().getMetamodel().entity(targetClass);


        StockQuoteWithAnnotation updateItem = updateItems.get(0);

        setPrimaryKeyCriteria(criteriaUpdate, updateItem);

        criteriaUpdate.set(StockQuoteWithAnnotation_.ask, updateItem.getAsk());

        jpa.commitTransaction((m) -> m.createQuery(criteriaUpdate).executeUpdate());

    }

    private void setPrimaryKeyCriteria(CriteriaUpdate<StockQuoteWithAnnotation> criteriaUpdate,
                                       StockQuoteWithAnnotation updateItem) {

        Path<LocalDateTime> primaryKey = criteriaUpdate.from(targetClass).get(PRIMARY_KEY);

        LocalDateTime primaryKeyValue = updateItem.getQuote_timestamp();

        criteriaUpdate.where(cb.equal(primaryKey, primaryKeyValue));
    }

    @Override
    public void delete() {

        jpa.commitTransaction((m) -> deleteFromTable(m, targetClass));
    }

    @Override
    public String primaryKeyName() {

        return StockQuoteWithAnnotation_.QUOTE_TIMESTAMP;
    }

    public List<StockQuoteWithAnnotation> find(CriteriaQuery<StockQuoteWithAnnotation> criteriaQuery) {

        return readFromTable(jpa.getEntityManager(), criteriaQuery);

    }

    public CriteriaQuery<StockQuoteWithAnnotation> createQueryOnPrimaryKey(StockQuoteWithAnnotation primaryKeyValue) {


        CriteriaQuery<StockQuoteWithAnnotation> criteriaQuery = cb.createQuery(targetClass);
        Path<LocalDateTime> primaryKey = criteriaQuery.from(targetClass).get(primaryKeyName());


        LocalDateTime pkValue = primaryKeyValue.getQuote_timestamp();
        criteriaQuery.where(cb.equal(primaryKey, pkValue));
        return criteriaQuery;
    }

}
