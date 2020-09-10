package com.github.jtam2000.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Objects;
import java.util.function.Consumer;

import static org.hibernate.boot.registry.StandardServiceRegistryBuilder.DEFAULT_CFG_RESOURCE_NAME;

public final class Hibernate implements AutoCloseable {

    private Session sessionInstance = null;
    private SessionFactory factory = null;
    private static Hibernate instance = null;

    static Hibernate getInstance() {

        return (instance = Objects.isNull(instance) ? new Hibernate() : instance);
    }

    public Session getSessionInstance() {

        return (sessionInstance = Objects.isNull(sessionInstance) ? factory.openSession() : sessionInstance);
    }

    public void commitTransaction(Consumer<Hibernate> task) {

        Transaction t =  getSessionInstance().beginTransaction();
        task.accept(getInstance());
        t.commit();
    }

    @Override
    public void close() throws Exception {

        System.out.println("closing Hibernate Session Instance");
        System.out.println("closing Hibernate Session Factory");
        sessionInstance.close();
        factory.close();
    }

    private Hibernate() {

        InitializeHibernate();
    }

    private void InitializeHibernate() {

        //ServiceRegistry and MetaData Sources
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure(DEFAULT_CFG_RESOURCE_NAME).build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        //Session Instance
        factory = meta.getSessionFactoryBuilder().build();

    }

}
