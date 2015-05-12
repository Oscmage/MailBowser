package edu.chl.mailbowser.account.models;

import edu.chl.mailbowser.email.models.Email;
import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.email.models.IEmail;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertTrue;

public class AccountTest {


    @Test
    public void testSend() throws Exception {
        IOutgoingServer outgoingServer = new MockOutServer();
        Account account = Account.INSTANCE;
        account.send(new Email(new ArrayList<IAddress>(),"",""));
        assertTrue(((MockOutServer)outgoingServer).called);

    }

    @Test
    public void testFetch() throws Exception {
        IIncomingServer incomingServer = new MockInServer();
        Account account = Account.INSTANCE;
        account.fetch();
        assertTrue(((MockInServer) incomingServer).called);

    }
}
