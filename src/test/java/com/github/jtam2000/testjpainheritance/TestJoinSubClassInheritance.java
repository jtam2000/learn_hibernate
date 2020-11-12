package com.github.jtam2000.testjpainheritance;

import com.github.jtam2000.jpa.dao.JPA;
import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import com.github.jtam2000.jpa.dao.JPARegistry;
import com.github.jtam2000.jpa.inheritance.joinedsubclass.PostageStamp;
import com.github.jtam2000.jpa.inheritance.joinedsubclass.PostalCountry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class TestJoinSubClassInheritance {

    private static final String jPUString = "jpu_inheritance_joined_subclass";

    private JPADataAccessDaoImpl<PostageStamp> dao;
    private JPARegistry<PostalCountry> postalRegistry;
    private final Class<PostageStamp> targetClass = PostageStamp.class;


    @Before
    public void runBeforeEachTest() {

        setUpJPUDao();
        registerAllCountries();

    }

    @After
    public void runAfterEachTest() {
        dao.close();
    }

    private void registerAllCountries() {

        Arrays.stream(PostalCountry.Country.values())
                .collect(Collectors.toList())
                .forEach(c -> postalRegistry.getFromRegistry(new PostalCountry(c)));
    }

    private void setUpJPUDao() {

        JPA jpa = new JPA(jPUString);

        dao = new JPADataAccessDaoImpl<>(jpa, targetClass);
        postalRegistry = new JPARegistry<>(jpa, PostalCountry.class);
    }

    @Test
    public void test_createPostageStamp() {

        dao.create(List.of(PostageStamp.of(new PostalCountry(PostalCountry.Country.CHINA))));
    }

}
