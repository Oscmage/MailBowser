package edu.chl.mailbowser.tests.account;

import edu.chl.mailbowser.account.Account;
import edu.chl.mailbowser.account.IAccount;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class AccountTest {
    Account account;

    MockAddress address;
    MockIncomingServer incomingServer;
    MockOutgoingServer outgoingServer;
    MockEmail email;
    MockTagHandler tagHandler;

    @Before
    public void setUp() {
        // setup mock objects and create an account to perform tests on
        address = new MockAddress();
        incomingServer = new MockIncomingServer();
        outgoingServer = new MockOutgoingServer();
        email = new MockEmail();
        tagHandler = new MockTagHandler();
        account = new Account(address, "", incomingServer, outgoingServer, tagHandler);
    }

    @Test
    public void testConstructor() throws Exception {
        // tests if IllegalArgumentException is thrown when creating an account with address null
        boolean exceptionThrown = false;
        try {
            new Account(null, "", incomingServer, outgoingServer, tagHandler);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        // tests if IllegalArgumentException is thrown when creating an account with password null
        exceptionThrown = false;
        try {
            new Account(address, null, incomingServer, outgoingServer, tagHandler);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        // tests if IllegalArgumentException is thrown when creating an account with incomingServer null
        exceptionThrown = false;
        try {
            new Account(address, "", null, outgoingServer, tagHandler);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        // tests if IllegalArgumentException is thrown when creating an account with outgoingServer null
        exceptionThrown = false;
        try {
            new Account(address, "", incomingServer, null, tagHandler);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        // tests if IllegalArgumentException is thrown when creating an account with tagHandler null
        exceptionThrown = false;
        try {
            new Account(address, "", incomingServer, outgoingServer, null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void testSend() throws Exception {
        // tests if the send method in outgoingServer is called, and the setSender method in the email is called
        account.send(email);
        assertTrue(outgoingServer.sendCalled);
        assertTrue(email.setSenderCalled);

        // tests if you can send a null email
        boolean exceptionThrown = false;
        try {
            account.send(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void testFetch() throws Exception {
        // tests if the fetch method in incomingServer is called
        account.fetch();
        assertTrue(incomingServer.fetchCalled);
    }

    @Test
    public void testRefetch() throws Exception {
        // tests if the fetch method in incomingServer is called
        account.refetch();
        assertTrue(incomingServer.fetchCalled);
    }

    @Test
    public void testTestConnect() throws Exception {
        // tests if the testConnection method in incomingServer is called
        account.testConnect();
        assertTrue(incomingServer.testConnectionCalled);
    }

    @Test
    public void testEquals() throws Exception {
        // tests that an account is equal to itself
        assertEquals(account, account);

        // tests if an account is equal to an identical account
        IAccount a1 = new Account(address, "", incomingServer, outgoingServer, tagHandler);
        assertEquals(account, a1);

        // tests if an account to another account when all that differs is the password
        IAccount a2 = new Account(address, "123", incomingServer, outgoingServer, tagHandler);
        assertNotEquals(account, a2);

        // tests if an account is equal to null
        assertNotEquals(account, null);
    }

    @Test
    public void testHashCode() throws Exception {
        // tests that an accounts hash code doesn't differ from time to time
        assertEquals(account.hashCode(), account.hashCode());

        // tests if an accounts hash code is equal to the hash code of an identical account
        IAccount a1 = new Account(address, "", incomingServer, outgoingServer, tagHandler);
        assertEquals(account.hashCode(), a1.hashCode());

        // tests if an accounts hash code is equal to another accounts hash code when all that differs is the password
        IAccount a2 = new Account(address, "123", incomingServer, outgoingServer, tagHandler);
        assertNotEquals(account.hashCode(), a2.hashCode());
    }
}
