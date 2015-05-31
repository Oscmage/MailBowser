package edu.chl.mailbowser.tests.account;

import edu.chl.mailbowser.account.Account;
import edu.chl.mailbowser.account.AccountHandler;
import edu.chl.mailbowser.account.IAccount;
import edu.chl.mailbowser.account.IAccountHandler;
import edu.chl.mailbowser.tests.mock.MockAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountHandlerTest {

    AccountHandler accountHandler1;
    AccountHandler accountHandler2;
    AccountHandler accountHandler3;
    MockAccount account1;

    @Before
    public void setUp() throws Exception {
        accountHandler1 = new AccountHandler();
        accountHandler2 = new AccountHandler();
        accountHandler3 = new AccountHandler();
        account1 = new MockAccount();
    }

    @After
    public void  tearDown() throws Exception{
        accountHandler1.getAccounts().clear();
        accountHandler2.getAccounts().clear();
        accountHandler3.getAccounts().clear();
    }
    @Test
    public void testAddAndRemoveAccount() throws Exception {
        accountHandler1.addAccount(account1);
        assertEquals(accountHandler1.getAccounts().size(),1);
        accountHandler1.removeAccount(account1);
        assertEquals(accountHandler1.getAccounts().size(),0);
    }

    @Test
    public void testInitFetchingFromAllAccounts() throws Exception {
        accountHandler1.addAccount(account1);
        accountHandler1.initFetchingFromAllAccounts();
        assertTrue(account1.fetchIsCalled);
    }

    @Test
    public void testInitRefetchingFromAllAccounts() throws Exception {
        accountHandler1.addAccount(account1);
        accountHandler1.initRefetchingFromAllAccounts();
        assertTrue(account1.refetchIsCalled);
    }

    @Test
    public void testEquals() throws Exception {
        accountHandler1.addAccount(account1);
        assertNotEquals(accountHandler1, accountHandler3);
        assertEquals(accountHandler1, (accountHandler1));
        assertEquals(accountHandler2, (accountHandler3));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(accountHandler1.hashCode(), accountHandler1.hashCode());
        accountHandler1.addAccount(account1);
        assertNotEquals(accountHandler1.hashCode(), accountHandler2.hashCode());
    }
}