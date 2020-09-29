package com.github.jtam2000.jpa;

import com.github.jtam2000.stockquotes.DataAccessObject;
import com.github.jtam2000.stockquotes.StockQuoteWithAnnotation;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import java.util.List;
import java.util.Set;

public interface JPADataAccessObject<T> extends DataAccessObject<T> {

    default TypedQuery<T> fromTableTypedQuery(EntityManager em, Class <T> inputClass) {

        return em.createQuery("from " + inputClass.getSimpleName(), inputClass);
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
}
