package com.github.jtam2000.testjpa.testjointablebionetoonemapping;

import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import com.github.jtam2000.jpa.relationships.jointableonetoone.BiInvestmentAccount;
import com.github.jtam2000.jpa.relationships.jointableonetoone.BiInvestmentUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestJoinTableBiDirectionOneToOne {
    @Test
    public void test_JoinTableBiDirectionalOneToOne() {

        System.out.println("testing BiDirectional one to one using JPA @JoinTable annotation");
    }


    private static final double ASSERT_DOUBLE_TOLERANCE = 0.0001;
    @SuppressWarnings("FieldCanBeLocal")
    private final String jPUString = "jpu_jointable_bidirectional_one_to_one";
    private JPADataAccessDaoImpl<BiInvestmentUser> userDao;
    private final Class<BiInvestmentUser> targetClass = BiInvestmentUser.class;

    private final String userName = "Jason Tam";
    private final LocalDate userCreationDate = LocalDate.now();
    private final String referringAgency = "Goldman Sachs";

    private final String accountName = "Natalie College Fund";
    private final String accountPurpose = "Investment account for daughter's college funds";
    private final double balance = 2000.19D;

    private BiInvestmentUser user;
    private BiInvestmentAccount account;

    @Before
    public void runBeforeEachTest() {

        setupJPUBeforeOtherSetup();
    }

    @After
    public void runAfterEachTest() {
        userDao.close();
    }

    private void setupJPUBeforeOtherSetup() {

        setUpJPUDao();
        setUpUser();
        setUpAccount();
        makeTablesEmptyForTesting();
    }

    private void makeTablesEmptyForTesting() {

        userDao.delete();

        deleteContentOfAccountTable();
    }

    private void deleteContentOfAccountTable() {

        Class<BiInvestmentAccount> accountClass = BiInvestmentAccount.class;
        JPADataAccessDaoImpl<BiInvestmentAccount> accountDao = new JPADataAccessDaoImpl<>(jPUString, accountClass);
        accountDao.delete();
    }

    private List<BiInvestmentAccount> getContentOfAccountTable() {

        Class<BiInvestmentAccount> accountClass = BiInvestmentAccount.class;
        JPADataAccessDaoImpl<BiInvestmentAccount> accountDao = new JPADataAccessDaoImpl<>(jPUString, accountClass);
        return accountDao.read();
    }

    private void setUpUser() {

        user = createDefaultUser();
    }

    private void setUpAccount() {

        account = createDefaultAccount();
    }

    private void setUpJPUDao() {

        userDao = new JPADataAccessDaoImpl<>(jPUString, targetClass);
    }

    private BiInvestmentUser createDefaultUser() {

        return new BiInvestmentUser(userName, userCreationDate, referringAgency);
    }

    private BiInvestmentAccount createDefaultAccount() {

        return new BiInvestmentAccount(accountName, accountPurpose, balance, user);
    }

    @Test
    public void test_createUser() {

        System.out.println("displaying Investment user:\n" + user);

        assertEquals("user name should equal:", userName, user.getUserName());
        assertEquals("user creation date should equal:", userCreationDate, user.getUserCreationDate());
        assertEquals("user referring agency should equal:", referringAgency, user.getReferringAgency());
        assertEquals("user id should default to zero:", 0L, user.getUserID());

    }

    @Test
    public void test_createUserAccount() {

        System.out.println("displaying user account:\n" + account);

        assertEquals("user account name should equal:", accountName, account.getAccountName());
        assertEquals("user account purpose should equal:", accountPurpose, account.getAccountPurpose());
        assertEquals("user account balance should equal:", balance, account.getBalance(), 0.0001);
        assertEquals("user account id should default to zero:", 0L, account.getAccountID());
    }

    @Test
    public void test_BiInvestmentUserHasOneBiInvestmentAccount() {

        user.addAccount(account);

        BiInvestmentAccount expectedAccount = createDefaultAccount();
        assertEquals("added account to user should be equal:", expectedAccount, user.getAccount());
    }

    @Test
    public void test_BiInvestmentAccountHasOneBiInvestmentUser() {
        BiInvestmentUser expectedUser = createDefaultUser();
        account.setUser(expectedUser);


        assertEquals("added user to account should be equal:", expectedUser, account.getUser());
    }

    @Test
    //CRud
    public void test_saveUserAndAccountUsingPersistCascade() {

        //given: see @Before

        //when
        createOneUserAndOneAccount();
        BiInvestmentUser foundUser = userDao.findByPrimaryKey(user);

        //then
        assertUserAndAccountPersisted(foundUser);
    }

    private void createOneUserAndOneAccount() {

        user.addAccount(account);
        account.setUser(user); //LEARNING: specifically make the relationship bidirectional
        userDao.create(List.of(user));

    }

    private void assertUserAndAccountPersisted(BiInvestmentUser foundUser) {

        assertUserPersisted(foundUser);
        assertUserAccountPersisted(foundUser);
    }

    private void assertUserAccountPersisted(BiInvestmentUser foundUser) {

        System.out.println("account saved for user:" + account);
        System.out.println("account queried for user:" + foundUser.getAccount());
        assertEquals("account saved and queried user account should be the same:", account, foundUser.getAccount());
    }

    private void assertUserPersisted(BiInvestmentUser foundUser) {

        System.out.println("user sent to save:" + user);
        System.out.println("user found on db:" + foundUser);
        assertEquals("user saved and user queried should be the same:", user, foundUser);
    }

    @Test
    //crUd
    public void test_UpdateAttributeInDependentEntity() {

        //given
        createOneUserAndOneAccount();

        //when
        double addAmount = 100;
        double expectNewAmountAfterAdd = getExpectedBalance(addAmount);
        updateAccountBalance(addAmount);

        //then
        List<BiInvestmentAccount> accounts = getContentOfAccountTable();
        assertEquals("after update account balance, the balance on db should be the same as update balance: ",
                expectNewAmountAfterAdd, accounts.get(0).getBalance(), ASSERT_DOUBLE_TOLERANCE);
    }

    double getExpectedBalance(double addAmount) {

        BiInvestmentAccount acct = user.getAccount();
        double currentBalance = acct.getBalance();
        return currentBalance + addAmount;
    }

    private void updateAccountBalance(double addAmount) {

        account.incrementBalance(addAmount);

        userDao.update(List.of(user));

    }

    @Test
    //cruD
    public void test_deleteUserAndAccountUsingPersistCascade() {

        //given
        createOneUserAndOneAccount();

        List<BiInvestmentUser> inDb = userDao.read();
        assertEquals("there should be 1 user to account relationship",1,  inDb.size());

        //when
        //Note: must NOT delete the entire table using delete(), since table delete does not trigger
        //the user delete of the account associated with the user
        userDao.delete(List.of(user));


        //then
        assertUserAndAccountTableAreBothEmpty();

    }

    private void assertUserAndAccountTableAreBothEmpty() {

        BiInvestmentUser foundUser = userDao.findByPrimaryKey(user);
        assertNull("should not be able to find the user after delete", foundUser);

        List<BiInvestmentAccount> accounts = getContentOfAccountTable();
        assertEquals("there should be zero accounts after user delete", 0, accounts.size());

    }

}
