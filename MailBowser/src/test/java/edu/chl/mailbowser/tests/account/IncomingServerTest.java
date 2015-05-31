package edu.chl.mailbowser.tests.account;

import edu.chl.mailbowser.account.IIncomingServer;
import edu.chl.mailbowser.account.IncomingServer;
import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.utils.Callback;
import edu.chl.mailbowser.utils.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IncomingServerTest {

    IIncomingServer incomingServer1;
    IIncomingServer incomingServer2;
    IIncomingServer incomingServer3;

    @Before
    public void init() {
        incomingServer1 = new IncomingServer("imap.gmail.com", "993");
        incomingServer2 = new IncomingServer("imap-mail.outlook.com", "25");
        incomingServer3 = new IncomingServer("imap.gmail.com", "993");
    }

    @Test
    /*
    since this test requires communication with actual mailservers
    this can not be an actual unit test but rather an integration test
     */
    public void testTestConnection() throws Exception {
        assertTrue(incomingServer1.testConnection("mailbows3r@gmail.com", "bowsmail3r"));
    }

    @Test
    /*
    since this test requires communication with actual mailservers
    this can not be an actual unit test but rather an integration test
     */
    public void testFetch() throws Exception {
        incomingServer1.fetch("mailbows3r@gmail.com", "bowsmail3r", true, new Callback<Pair<IEmail, String>>() {
            @Override
            public void onSuccess(Pair<IEmail, String> object) {
                //will only be called if fetch was successful
                assertTrue(true);
            }

            @Override
            public void onFailure(String msg) {
                //will only be called if fetch was a failure
                assertFalse(true);
            }
        });
    }

    @Test
    public void testHashCode() throws Exception {
        assertTrue(incomingServer1.hashCode() == incomingServer1.hashCode());
        assertFalse(incomingServer1.hashCode() == incomingServer3.hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(incomingServer1.equals(incomingServer1));
        assertTrue(incomingServer1.equals(incomingServer3));
        assertFalse(incomingServer1.equals(incomingServer2));
    }
}