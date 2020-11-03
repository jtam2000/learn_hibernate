package com.github.jtam2000.jpa;

import com.github.jtam2000.jpa.dao.JPA;
import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import com.github.jtam2000.jpa.relationships.jointableonetoone.BiInvestmentUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestJPAEntities {

    private JPADataAccessDaoImpl<BiInvestmentUser> userDao;
    private final Class<BiInvestmentUser> targetClass = BiInvestmentUser.class;
    private JPA jpa;

    @Test
    public void test_findJPAEntityAnnotatedName() {

        String entityName = userDao.getTableName(jpa.getEntityManager(), BiInvestmentUser.class);
        assertEquals("JointTableInvestmentUser", entityName);
    }

    @After
    public void runAfterEachTest() {

        userDao.close();
    }

    @Before
    public void setUpJPUDao() {

        String jPUString = "jpu_jointable_bidirectional_one_to_one";
        jpa = new JPA(jPUString);
        userDao = new JPADataAccessDaoImpl<>(jpa, targetClass);
    }

}
