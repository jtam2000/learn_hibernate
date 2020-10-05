package com.github.jtam2000.testhibernate;

import com.github.jtam2000.hibernate.DemoStockQuoteInHibernateUsingAnnotation;
import com.github.jtam2000.hibernate.DemoStockQuoteInHibernateUsingConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;

public class TestHibernate {

    private static final  Clock testClock = Clock.systemDefaultZone();

    private DemoStockQuoteInHibernateUsingAnnotation annoDemo;

    @Before
    public void setup() {

        annoDemo = new DemoStockQuoteInHibernateUsingAnnotation();
    }
    @Test
    public void testHibernate_DemoWithConfiguration() {
        DemoStockQuoteInHibernateUsingConfiguration.demoStockQuoteWithConfiguration();
    }

    @Test
    public void testHibernate_DemoWithAnnotation() {
        DemoStockQuoteInHibernateUsingAnnotation.demoStockQuoteWithAnnotations();
    }


}
