package com.github.jtam2000.jpa.compositeprimarykey;

import com.github.jtam2000.jpa.HasPrimaryKey;
import com.github.jtam2000.jpa.dao.JPA;
import com.github.jtam2000.jpa.dao.JPADataAccessObject;

import java.util.List;

public class CompositeKeyWithIdClassAnnotationDao implements JPADataAccessObject<CompositeKeyWithIdClassAnnotation> {

    private JPA jpa;

    private static final Class<CompositeKeyWithIdClassAnnotation> targetClass = CompositeKeyWithIdClassAnnotation.class;

    public CompositeKeyWithIdClassAnnotationDao(JPA jpa) {
        this.jpa = jpa;
    }

    @Override
    public void create(List<? extends HasPrimaryKey> items) {

        create(jpa, items);

    }

    @Override
    public List<CompositeKeyWithIdClassAnnotation> read() {
        return null;
    }

    @Override
    public List<CompositeKeyWithIdClassAnnotation> readByPrimaryKey(List<? extends HasPrimaryKey> pks) {
        return null;
    }

    @Override
    public void update(List<? extends HasPrimaryKey>items) {
        update(items, jpa);
    }

    @Override
    public void delete() {
        jpa.commitTransaction((m) -> deleteFromTable(m, targetClass));
    }

    @Override
    public void delete(List<? extends HasPrimaryKey> items) {
        delete(jpa, targetClass, items);
    }

    @Override
    public String primaryKeyName() {
        return null;
    }


}
