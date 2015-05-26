package edu.chl.mailbowser.tests.account.models;

import edu.chl.mailbowser.account.models.Account;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertTrue;

public class AccountTest {
    Account account;
    MockInServer incomingServer;
    MockOutServer outgoingServer;
    MockEmail email;
    MockTagHandler tagHandler;

    @Before
    public void init(){
        incomingServer = new MockInServer();
        outgoingServer = new MockOutServer();
        email = new MockEmail();
        tagHandler = new MockTagHandler();
        account = new Account(new MockAddress(), "", incomingServer, outgoingServer, tagHandler);
    }

    @Test
    public void testSend() throws Exception {
        account.send(email);
        assertTrue(outgoingServer.called);
        assertTrue(email.setSender);
    }

    @Test
    public void testFetch() throws Exception {
        account.fetch();
        assertTrue(incomingServer.called);
    }
}
