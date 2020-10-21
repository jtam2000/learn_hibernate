package com.github.jtam2000.jpa.dao;

import com.github.jtam2000.jpa.HasPrimaryKey;
import com.github.jtam2000.jpa.dao.JPA;
import com.github.jtam2000.stockquotes.DataAccessObject;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public interface JPADataAccessObject<T extends HasPrimaryKey> extends DataAccessObject<T> {


    default TypedQuery<T> fromTableTypedQuery(EntityManager em, Class <T> inputClass) {

        return em.createQuery("from " + getTableName(inputClass), inputClass);
    }

    default List<T> readFromTable(EntityManager em, Class <T> inputClass) {

        return fromTableTypedQuery(em, inputClass).getResultList();
    }

    default List<T> readFromTable(EntityManager em, CriteriaQuery<T> criteriaQuery) {

        return em.createQuery(criteriaQuery).getResultList();
    }

    default void deleteFromTable(EntityManager em, Class<T> inputClass) {

        em.createQuery("delete from " + getTableName(inputClass)).executeUpdate();
    }

    default String getTableName(Class <T> inputClass) {

        return inputClass.getSimpleName();
    }

    default void updateTable(EntityManager em, Class<T> inputClass, CriteriaUpdate<T> updateCriteria) {
        updateCriteria.from(inputClass);
        em.createQuery(updateCriteria).executeUpdate();
    }

    //CRUD methods:
    default void create(JPA jpa, List<? extends HasPrimaryKey> items) {

        items.forEach((i) -> jpa.commitTransaction((m) -> m.persist(i)));
    }

    default List<T> read(JPA jpa, Class<T> targetClass) {
        return readFromTable(jpa.getEntityManager(), targetClass);

    }

    default List<T> readByPrimaryKey(JPA jpa, Class<T> targetClass, List<? extends HasPrimaryKey> pks) {

        ArrayList<T> rtn = new ArrayList<>();
        pks.forEach(i -> rtn.add(findByPrimaryKey(jpa, targetClass, i)));
        return rtn;
    }

    default void update(List<? extends HasPrimaryKey> items, JPA jpa) {
        items.forEach((i) -> jpa.commitTransaction((m) -> m.persist(i)));
    }

    default  T findByPrimaryKey(JPA jpa, Class<T> targetClass, HasPrimaryKey item) {

        return jpa.getEntityManager().find(targetClass, item.getPrimaryKey());
    }

    default void delete(JPA jpa, Class<T> targetClass) {
        jpa.commitTransaction((m) -> deleteFromTable(m, targetClass));

    }
    @Transactional
    default void delete(JPA jpa, Class<T> targetClass, List<? extends HasPrimaryKey> items) {

        items.forEach((i) -> jpa.commitTransaction((m) -> m.remove(i)));
    }

    default void refresh(JPA jpa, Class<T> targetClass, List<? extends HasPrimaryKey> items) {
        items.forEach((i) -> jpa.getEntityManager().refresh(i));
    }
}
