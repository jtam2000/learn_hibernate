package com.github.jtam2000.jpa.dao;

import com.github.jtam2000.jpa.HasPrimaryKey;

import java.util.List;

public class JPARegistry<T extends HasPrimaryKey> extends JPADataAccessDaoImpl<T> {


    public JPARegistry(JPA jpa, Class<T> targetClass) {
        super(jpa, targetClass);

    }

    public List<T> getFromRegistry(List<T> items) {

        return findOrCreate(items);
    }
    public T getFromRegistry(T item) {

        return getFromRegistry(List.of(item)).get(0);
    }
}
