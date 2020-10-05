package com.github.jtam2000.jpa.jpaprimarykey;

import com.github.jtam2000.jpa.JPA;
import com.github.jtam2000.jpa.JPADataAccessObject;
import com.github.jtam2000.stockquotes.StockQuoteWithAnnotation;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class SinglePrimaryKeyDao implements JPADataAccessObject<SinglePrimaryKey> {

    private JPA jpa;

    private static final Class<SinglePrimaryKey> targetClass = SinglePrimaryKey.class;

    public SinglePrimaryKeyDao(JPA jpa) {
        this.jpa = jpa;
    }


    @Override
    public void create(List<SinglePrimaryKey> items) {

        items.forEach((i) -> jpa.commitTransaction((m) -> m.persist(i)));
    }


    @Override
    public List<SinglePrimaryKey> read() {
        return readFromTable(jpa.getEntityManager(), targetClass);
    }

    @Override
    public List<SinglePrimaryKey> readByPrimaryKey(List<SinglePrimaryKey> pks) {

        ArrayList<SinglePrimaryKey> rtn = new ArrayList<>();
        pks.forEach(i -> rtn.add(jpa.getEntityManager().find(targetClass, i)));
        return rtn;
    }

    @Override
    public void update(List<SinglePrimaryKey> items) {

        items.forEach(jpa.getEntityManager()::persist);
    }

    @Override
    public void delete() {
        jpa.commitTransaction((m) -> deleteFromTable(m, targetClass));
    }

    @Override
    public void delete(List<SinglePrimaryKey> items) {

        items.forEach((i) -> jpa.commitTransaction((m) -> m.remove(i)));

    }

    private void delete(EntityManager em) {

    }

    @Override
    public String primaryKeyName() {
        return null;
    }

    public SinglePrimaryKey findByPrimaryKey(SinglePrimaryKey item) {

        return jpa.getEntityManager().find(targetClass, item.getPrimaryKey());

    }

}
