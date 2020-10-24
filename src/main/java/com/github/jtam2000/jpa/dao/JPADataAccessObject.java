package com.github.jtam2000.jpa.dao;

import com.github.jtam2000.jpa.HasPrimaryKey;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface JPADataAccessObject<T extends HasPrimaryKey> extends DataAccessObject<T> {


    default TypedQuery<T> fromTableTypedQuery(EntityManager em, Class<T> inputClass) {

        return em.createQuery("from " + getTableName(inputClass), inputClass);
    }

    default List<T> readFromTable(EntityManager em, Class<T> inputClass) {

        return fromTableTypedQuery(em, inputClass).getResultList();
    }
    @SuppressWarnings({"unused", "RedundantSuppression"})
    default List<T> readFromTable(EntityManager em, CriteriaQuery<T> criteriaQuery) {

        return em.createQuery(criteriaQuery).getResultList();
    }

    default void deleteFromTable(EntityManager em, Class<T> inputClass) {

        em.createQuery("delete from " + getTableName(inputClass)).executeUpdate();
    }

    default String getTableName(Class<T> inputClass) {

        return inputClass.getSimpleName();
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    default void updateTable(EntityManager em, Class<T> inputClass, CriteriaUpdate<T> updateCriteria) {

        updateCriteria.from(inputClass);
        em.createQuery(updateCriteria).executeUpdate();
    }

    //CRUD methods:
    default void create(JPA jpa, List<? extends HasPrimaryKey> items) {

        //items.forEach((i) -> jpa.commitTransaction((m) -> m.persist(i)));
        jpa.commitTransaction((m) -> items.forEach(m::persist));
    }

    default List<T> read(JPA jpa, Class<T> targetClass) {

        return readFromTable(jpa.getEntityManager(), targetClass);

    }

    default List<T> readByPrimaryKey(JPA jpa, Class<T> targetClass, List<? extends HasPrimaryKey> pks) {

        ArrayList<T> rtn = new ArrayList<>();
        pks.forEach(i -> rtn.add(findByPrimaryKey(jpa, targetClass, i)));
        return rtn;
    }

    default List<T> findOrCreate(JPA jpa, Class<T> targetClass, List<T> pks) {

        Map<Boolean, List<T>> map = createMapOfItemsInOrNotInDB(jpa, targetClass, pks);

        List<T> createList = createItemsInDBIfNotFound(map);
        return createFullListRequested(createList, map);
    }

    private Map<Boolean, List<T>> createMapOfItemsInOrNotInDB(JPA jpa, Class<T> targetClass, List<T> pks) {

        return pks.stream().collect(
                Collectors.partitioningBy(itemExistsInDB(jpa, targetClass))
        );

    }


    private List<T> createFullListRequested(List<T> createList, Map<Boolean, List<T>> map) {

        List<T> fullList = map.get(true);
        fullList.addAll(createList);
        return fullList;
    }

    private List<T> createItemsInDBIfNotFound(Map<Boolean, List<T>> map) {

        List<T> createList = map.get(false);
        //persist: create in db
        create(createList);

        return createList;
    }


    private Predicate<T> itemExistsInDB(JPA jpa, Class<T> targetClass) {

        return item -> findByPrimaryKey(jpa, targetClass, item) != null;
    }

    default void update(List<? extends HasPrimaryKey> items, JPA jpa) {

        //items.forEach((i) -> jpa.commitTransaction((m) -> m.persist(i)));
        jpa.commitTransaction((m) -> items.forEach(m::persist));
    }

    default T findByPrimaryKey(JPA jpa, Class<T> targetClass, HasPrimaryKey item) {

        return jpa.getEntityManager().find(targetClass, item.getPrimaryKey());
    }

    default void delete(JPA jpa, Class<T> targetClass) {

        jpa.commitTransaction((m) -> deleteFromTable(m, targetClass));

    }

    @Transactional
    default void delete(JPA jpa, List<? extends HasPrimaryKey> items) {

        if (Objects.nonNull(items))
            jpa.commitTransaction((m) -> items.forEach(m::remove));
    }

    default void refresh(JPA jpa, List<? extends HasPrimaryKey> items) {

        items.forEach((i) -> jpa.getEntityManager().refresh(i));
    }

    default void rollbackTransaction(JPA jpa) {

        jpa.rollbackTransaction();
    }
}
