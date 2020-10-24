package com.github.jtam2000.jpa.dao;

import com.github.jtam2000.jpa.HasPrimaryKey;

import java.util.List;
import java.util.Objects;

public class JPADataAccessDaoImpl<T extends HasPrimaryKey> implements JPADataAccessObject<T>, AutoCloseable{

    private static JPA jpa;
    private final Class<T> targetClass;

    public JPADataAccessDaoImpl(String jpaString, Class<T> targetClass) {

        jpa = new JPA(jpaString);
        this.targetClass = targetClass;
    }

    @Override
    public void rollbackTransaction() {

        rollbackTransaction(jpa);
    }

    public JPADataAccessDaoImpl(JPA jpa, Class<T> targetClass) {

        JPADataAccessDaoImpl.jpa = jpa;
        this.targetClass = targetClass;
    }

    @Override
    public void create(List<? extends HasPrimaryKey> items) {
        create(jpa, items);
    }

    @Override
    public List<T> read() {

        return read(jpa, targetClass);
    }

    @Override
    public List<T> readByPrimaryKey(List<? extends HasPrimaryKey> pks) {

        return readByPrimaryKey(jpa, targetClass, pks);
    }

    @Override
    public List<T> findOrCreate(List<T> pks) {

        return findOrCreate(jpa,targetClass, pks);
    }

    @Override
    public void update(List<? extends HasPrimaryKey> items) {

        update(items, jpa);
    }

    @Override
    public void delete() {

        delete(jpa, targetClass);
    }

    @Override
    public void delete(List<? extends HasPrimaryKey> items) {

        delete(jpa, items);
    }

    @Override
    public void refresh(List<? extends HasPrimaryKey> items) {
        refresh(jpa, items);
    }

    @Override
    public String primaryKeyName(HasPrimaryKey pk) {
        return pk.getPrimaryKeyName();
    }

    public T findByPrimaryKey(HasPrimaryKey pk) {

        return findByPrimaryKey(jpa, targetClass, pk);
    }


    @Override
    public void close() {

        if (Objects.nonNull(jpa))
            jpa.close();
    }
}
