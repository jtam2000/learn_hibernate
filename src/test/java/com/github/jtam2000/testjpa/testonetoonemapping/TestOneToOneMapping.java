package com.github.jtam2000.testjpa.testonetoonemapping;

import com.github.jtam2000.jpa.dao.JPADataAccessDaoImpl;
import com.github.jtam2000.jpa.relationships.onetoone.InvestmentAccount;
import com.github.jtam2000.jpa.relationships.onetoone.InvestmentUser;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SuppressWarnings("FieldMayBeFinal")
public class TestOneToOneMapping {

    private static final double ASSERT_DOUBLE_TOLERANCE = 0.0001;
    @SuppressWarnings("FieldCanBeLocal")
    private final String jPUString = "jpu_relationship_one_to_one";
    private JPADataAccessDaoImpl<InvestmentUser> userDao;
    private final Class<InvestmentUser> targetClass = InvestmentUser.class;

    private String userName = "Jason Tam";
    private LocalDate userCreationDate = LocalDate.now();
    private String referringAgency = "Goldman Sachs";

    private String accountName = "Natalie College Fund";
    private String accountPurpose = "Investment account for daughter's college funds";
    private double balance = 2000.19D;

    private InvestmentUser user;
    private InvestmentAccount account;

    @Before
    public void runBeforeEachTest() {

        setupJPUBeforeOtherSetup();
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

        Class<InvestmentAccount> accountClass = InvestmentAccount.class;
        JPADataAccessDaoImpl<InvestmentAccount> accountDao = new JPADataAccessDaoImpl<>(jPUString, accountClass);
        accountDao.delete();
    }

    private List<InvestmentAccount> getContentOfAccountTable() {

        Class<InvestmentAccount> accountClass = InvestmentAccount.class;
        JPADataAccessDaoImpl<InvestmentAccount> accountDao = new JPADataAccessDaoImpl<>(jPUString, accountClass);
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

    private InvestmentUser createDefaultUser() {

        return new InvestmentUser(userName, userCreationDate, referringAgency);
    }

    private InvestmentAccount createDefaultAccount() {

        return new InvestmentAccount(accountName, accountPurpose, balance);
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
    public void test_InvestmentUserHasOneInvestmentAccount() {

        user.addAccount(account);

        InvestmentAccount expectedAccount = createDefaultAccount();
        assertEquals("added account to user should be equal:", expectedAccount, user.getAccount());
    }

    @Test
    //CRud
    public void test_saveUserAndAccountUsingPersistCascade() {

        //given: see @Before

        //when
        createOneUserAndOneAccount();
        InvestmentUser foundUser = userDao.findByPrimaryKey(user);

        //then
        assertUserAndAccountPersisted(foundUser);
    }

    private void createOneUserAndOneAccount() {

        user.addAccount(account);
        userDao.create(List.of(user));
    }

    private void assertUserAndAccountPersisted(InvestmentUser foundUser) {

        assertUserPersisted(foundUser);
        assertUserAccountPersisted(foundUser);
    }

    private void assertUserAccountPersisted(InvestmentUser foundUser) {

        System.out.println("account saved for user:" + account);
        System.out.println("account queried for user:" + foundUser.getAccount());
        assertEquals("account saved and queried user account should be the same:", account, foundUser.getAccount());
    }

    private void assertUserPersisted(InvestmentUser foundUser) {

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
        List<InvestmentAccount> accounts = getContentOfAccountTable();
        assertEquals("after update account balance, the balance on db should be the same as update balance: ",
                expectNewAmountAfterAdd, accounts.get(0).getBalance(), ASSERT_DOUBLE_TOLERANCE);
    }

    double getExpectedBalance(double addAmount) {

        InvestmentAccount acct = user.getAccount();
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

        //when
        //Note: must NOT delete the entire table using delete(), since table delete does not trigger
        //the user delete of the account associated with the user
        userDao.delete(List.of(user));


        //then
        assertUserAndAccountTableAreBothEmpty();

    }

    private void assertUserAndAccountTableAreBothEmpty() {

        InvestmentUser foundUser = userDao.findByPrimaryKey(user);
        assertNull("should not be able to find the user after delete", foundUser);

        List<InvestmentAccount> accounts = getContentOfAccountTable();
        assertEquals("there should be zero accounts after user delete", 0, accounts.size());

    }
}
