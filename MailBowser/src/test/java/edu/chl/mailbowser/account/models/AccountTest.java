package edu.chl.mailbowser.account.models;

import edu.chl.mailbowser.email.models.Address;
import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.email.models.IEmail;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertTrue;

public class AccountTest {
    Account account;
    MockInServer incomingServer;
    MockOutServer outgoingServer;
    MockEmail email;

    @Before
    public void init(){
        incomingServer = new MockInServer();
        outgoingServer = new MockOutServer();
        email = new MockEmail();
        account = new Account(new MockAddress(),"",incomingServer,outgoingServer);
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
