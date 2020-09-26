package com.github.jtam2000.jpa;

import com.github.jtam2000.stockquotes.DataAccessObject;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import java.util.List;
import java.util.Set;

public interface JPADataAccessObject<T> extends DataAccessObject<T> {

    default String getID(EntityManager em, Class<T> inputClass) {
        EntityType<T> x = em.getMetamodel().entity(inputClass);
        Type<?> y = x.getIdType();
        System.out.println("ID type:" + x.getIdType());
        //SingularAttribute<? super T, ? extends Type> m = x.getId(x.getIdType());

        //System.out.println("id Attributes:" + m);
        return null;
    }
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
