package com.github.jtam2000.hibernate.testhibernate;

import com.github.jtam2000.hibernate.DemoStockQuoteInHibernateUsingAnnotation;
import com.github.jtam2000.hibernate.DemoStockQuoteInHibernateUsingConfiguration;
import org.junit.Test;

public class TestHibernate {

    @Test
    public void testHibernate_WithConfiguration() {
        DemoStockQuoteInHibernateUsingConfiguration.demoStockQuoteWithConfiguration();
    }

    @Test
    public void testHibernate_WithAnnotation() {
        DemoStockQuoteInHibernateUsingAnnotation.demoStockQuoteWithAnnotations();

    }
}
