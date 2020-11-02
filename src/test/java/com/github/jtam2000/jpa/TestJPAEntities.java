package com.github.jtam2000.jpa;

import com.github.jtam2000.jpa.dao.JPA;
import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import com.github.jtam2000.jpa.relationships.jointableonetoone.BiInvestmentAccount;
import com.github.jtam2000.jpa.relationships.jointableonetoone.BiInvestmentUser;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDate;

public class TestJPAEntities {

    private final String jPUString = "jpu_jointable_bidirectional_one_to_one";
    private JPADataAccessDaoImpl<BiInvestmentUser> userDao;
    private final Class<BiInvestmentUser> targetClass = BiInvestmentUser.class;
    private JPA jpa;

    @Test
    public void test_findJPAEntities() {

        BiInvestmentUser user = new BiInvestmentUser("jason", LocalDate.now(), "");
        BiInvestmentAccount account = new BiInvestmentAccount("Test", "test purpose", 33.2D, user);
        setUpJPUDao();

        String entityName=userDao.getTableName(jpa.getEntityManager(), BiInvestmentUser.class);
        System.out.println(entityName);;
        System.out.println(entityName);;


    }

    @After
    public void runAfterEachTest() {
        userDao.close();
    }
    private void setUpJPUDao() {

        jpa = new JPA(jPUString);
        userDao = new JPADataAccessDaoImpl<BiInvestmentUser>(jpa,targetClass);
    }

}
