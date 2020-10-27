package com.github.jtam2000.testjpa.testunimanytomanymapping;

import com.github.jtam2000.jpa.dao.JPA;
import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import com.github.jtam2000.jpa.relationships.manytomany.Stamps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class TestUniDirectionManyToMany {

    private JPADataAccessDaoImpl<Stamps> dao;
    private final Class<Stamps> targetClass =  Stamps.class;

    @Before
    public void runBeforeEachTest() {

        createDaos();

    }

    @After
    public void runAfterEachTest() {
        dao.close();
    }

    private JPA createJPU() {

        String JPUString = "jpu_relationship_many_to_many";
        return new JPA(JPUString);
    }

    private void createDaos() {

        createDaos(createJPU());
    }

    private void createDaos(JPA jpu) {

        dao = new JPADataAccessDaoImpl<>(jpu, targetClass);
    }

    @Test
    //CRud
    public void test_createUnidirectional(){

        //given @Before:
        Stamps stamp= new Stamps("Year of the Pig 2010",
                "China", 0.22, LocalDate.of(2019, 2, 28));
        dao.create(List.of(stamp));

        Stamps found = dao.findByPrimaryKey(stamp);
        assertEquals( "created/persisted stamp should be same as queried:" , stamp, found);
    }

}
