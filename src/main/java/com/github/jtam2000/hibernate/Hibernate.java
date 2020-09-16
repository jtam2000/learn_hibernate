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

    public static final String DEFAULT_CFG_ANNOTATION = "hibernate.annotation.cfg.xml";

    private static Session sessionInstance = null;
    private static SessionFactory factoryInstance = null;
    private static Hibernate instance = null;
    private static Metadata meta;


    static Hibernate getInstance(String hbnConfigFile) {

        return (instance = Objects.isNull(instance) ? new Hibernate(hbnConfigFile) : instance);
    }

    static Hibernate getInstance() {

        return getInstance(DEFAULT_CFG_RESOURCE_NAME);
    }

    public Session getSessionInstance() {

        return (sessionInstance = Objects.isNull(sessionInstance) ?
                getFactoryInstance().openSession() : sessionInstance);
    }

    @SuppressWarnings("unused")
    private SessionFactory getFactoryInstance() {

        return factoryInstance = Objects.isNull(factoryInstance) ?
                meta.getSessionFactoryBuilder().build() : factoryInstance;
    }

    public void commitTransaction(Consumer<Hibernate> task) {

        Transaction t = getSessionInstance().beginTransaction();
        task.accept(getInstance());
        t.commit();
    }

    @Override
    public void close()  {

        System.out.println("closing Hibernate Session Instance");
        sessionInstance.close();
        System.out.println("closing Hibernate Session Factory");
        factoryInstance.close();
    }

    private Hibernate(String hbnConfigFile) {

        initializeHibernate(hbnConfigFile);
    }

    private void initializeHibernate(String hbnConfigFile) {

        //ServiceRegistry and MetaData Sources
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure(hbnConfigFile).build();
        meta = new MetadataSources(ssr).getMetadataBuilder().build();

        //Session Instance
        getFactoryInstance();
    }

}
