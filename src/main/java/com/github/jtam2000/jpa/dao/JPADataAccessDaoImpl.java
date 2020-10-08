package com.github.jtam2000.jpa.dao;

import com.github.jtam2000.jpa.HasPrimaryKey;
import com.github.jtam2000.jpa.compositeprimarykey.CompositeKeyWithIdClassAnnotation;
import com.github.jtam2000.jpa.primarykey.SinglePrimaryKey;

import java.util.List;

public class JPADataAccessDaoImpl<T extends HasPrimaryKey> implements JPADataAccessObject<T>{

    private static JPA jpa;
    private Class<T> targetClass = null;

    public JPADataAccessDaoImpl(JPA jpa, Class<T> targetClass) {

        this.jpa = jpa;
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
    public void update(List<? extends HasPrimaryKey> items) {

        update(items, jpa);
    }

    @Override
    public void delete() {

        delete(jpa, targetClass);
    }

    @Override
    public void delete(List<? extends HasPrimaryKey> items) {

        delete(jpa, targetClass, items);
    }

    @Override
    public void refresh(List<? extends HasPrimaryKey> items) {
        refresh(jpa, targetClass, items);
    }

    @Override
    public String primaryKeyName(HasPrimaryKey pk) {
        return pk.getPrimaryKeyName();
    }

    public T findByPrimaryKey(HasPrimaryKey pk) {

        return findByPrimaryKey(jpa, targetClass, pk);
    }


}
