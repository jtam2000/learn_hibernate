package com.github.jtam2000.jpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.function.Consumer;

public class JPA implements AutoCloseable {

    private final EntityManagerFactory emf;
    private final EntityManager em;
    private EntityTransaction transaction;

    public JPA(String jpu) {

        emf = Persistence.createEntityManagerFactory(jpu);
        em = emf.createEntityManager();
        transaction = em.getTransaction();
    }

    public void commitTransaction(Consumer<EntityManager> task) {

        transaction.begin();
        task.accept(em);
        transaction.commit();
    }

    public void rollbackTransaction() {

        transaction.rollback();
    }

    @Override
    public void close() {

        System.out.println("Closing EntityManager, Then Closing EntityManagerFactory");
        em.close();
        emf.close();
    }

    public EntityManager getEntityManager() {

        return em;
    }
}
