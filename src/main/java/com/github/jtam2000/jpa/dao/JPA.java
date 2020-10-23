package com.github.jtam2000.jpa.dao;

import com.github.jtam2000.stockquotes.StockQuoteWithAnnotation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.function.Consumer;

public class JPA implements AutoCloseable{

    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction transaction;

    public JPA(String jpu) {

        emf = Persistence.createEntityManagerFactory(jpu);
        em = emf.createEntityManager();
        EntityTransaction transaction;

    }

    public void commitTransaction(Consumer<EntityManager> task) {

        transaction = em.getTransaction();
        transaction.begin();
        task.accept(em);
        transaction.commit();

    }

    public EntityManager getEntityManager() {

        return em;
    }

    @Override
    public void close(){
        System.out.println("Closing EntityManager, Then Closing EntityManagerFactory");
        em.close();
        emf.close();
    }

    public void rollbackTransaction() {
        transaction.rollback();
    }
}
