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

    public static final String ANNOTATION_CONFIG = "hibernate.annotation.cfg.xml";

    private static StandardServiceRegistry ssr = null;
    private static Session sessionInstance = null;
    private static SessionFactory factoryInstance = null;
    private static Hibernate instance = null;
    private static Metadata meta;
    private static String currentConfigFile = null;

    static Hibernate getInstance(String hbnConfigFile) {

        if (Objects.isNull(hbnConfigFile) || hbnConfigFile.isEmpty())
            return getInstance();


        if (Objects.nonNull(instance))
            instance.close();
        instance = new Hibernate(hbnConfigFile);
        currentConfigFile = hbnConfigFile;

        return instance;
    }


    /*
        use the default Hibernate configuration file name: DEFAULT_CFG_RESOURCE_NAME
     */
    static Hibernate getInstance() {

        return Objects.isNull(instance) ?
                getInstance(DEFAULT_CFG_RESOURCE_NAME) : instance;
    }

    public Session getSessionInstance() {

        return (sessionInstance = Objects.isNull(sessionInstance) ?
                getFactoryInstance().openSession() : sessionInstance);
    }

    @SuppressWarnings("unused")
    private SessionFactory getFactoryInstance() {

        return (factoryInstance = Objects.isNull(factoryInstance) ?
                meta.getSessionFactoryBuilder().build() : factoryInstance);

    }

    @SuppressWarnings("unused")
    private void setMetaDataInstance() {

        meta = new MetadataSources(ssr).getMetadataBuilder().build();
    }


    @SuppressWarnings("unused")
    public void setFactoryProperties(String key, String newValue) {

        if (Objects.nonNull(factoryInstance))
            factoryInstance.getProperties().put(key, newValue);
    }

    @SuppressWarnings("unused")
    public void setSessionProperties(String key, String newValue) {
        if (Objects.nonNull(sessionInstance))
            sessionInstance.getProperties().put(key, newValue);
    }

    public void commitTransaction(Consumer<Hibernate> task) {

        Transaction t = getSessionInstance().beginTransaction();
        task.accept(getInstance());
        t.commit();
    }

    @Override
    public void close() {

        if (Objects.nonNull(sessionInstance)) {

            sessionInstance.close();
            System.out.println("closing Hibernate Session Instance");
            sessionInstance = null;
        }

        if (Objects.nonNull(factoryInstance)) {

            System.out.println("closing Hibernate Session Factory");
            factoryInstance.close();
            factoryInstance = null;
        }
    }

    private Hibernate(String hbnConfigFile) {

        initializeHibernate(hbnConfigFile);
    }

    private void initializeHibernate(String hbnConfigFile) {

        //ServiceRegistry and MetaData Sources
        ssr = new StandardServiceRegistryBuilder().configure(hbnConfigFile).build();

        setMetaDataInstance();

        //Session Instance
        getFactoryInstance();
    }

}
