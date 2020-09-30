package com.github.jtam2000.jpa;

import com.github.jtam2000.stockquotes.StockQuoteWithAnnotation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.function.Consumer;

public class JPA implements AutoCloseable{

    private EntityManagerFactory emf;
    private EntityManager em;

    public JPA(String jpu) {

        emf = Persistence.createEntityManagerFactory(jpu);
        em = emf.createEntityManager();

    }

    public void commitTransaction(Consumer<EntityManager> task) {

        EntityTransaction t = em.getTransaction();

        t.begin();
        task.accept(em);
        t.commit();
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
}
