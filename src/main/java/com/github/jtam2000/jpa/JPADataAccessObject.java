package com.github.jtam2000.jpa;

import com.github.jtam2000.stockquotes.DataAccessObject;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import java.util.List;

public interface JPADataAccessObject<T> extends DataAccessObject<T> {

    default TypedQuery<T> fromTableTypedQuery(EntityManager em, Class <T> inputClass) {

        return em.createQuery("from " + inputClass.getSimpleName(), inputClass);
    }

    default List<T> readFromTable(EntityManager em, Class <T> inputClass) {

        return fromTableTypedQuery(em, inputClass).getResultList();
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
}
