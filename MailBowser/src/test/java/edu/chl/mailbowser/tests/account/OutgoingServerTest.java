package edu.chl.mailbowser.tests.account;

import edu.chl.mailbowser.account.IOutgoingServer;
import edu.chl.mailbowser.account.OutgoingServer;
import edu.chl.mailbowser.email.Email;
import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.tests.mock.MockEmail;
import edu.chl.mailbowser.utils.Callback;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OutgoingServerTest {

    IOutgoingServer outgoingServer1;

    @Before
    public void setUp() throws Exception {
        outgoingServer1 = new OutgoingServer("smtp.gmail.com", "587");
    }

    @Test
    /*
    since this test requires communication with actual mailservers
    this can not be an actual unit test but rather an integration test
     */
    public void testSend() throws Exception {
        outgoingServer1.send(new MockEmail(), "mailbows3r@gmail", "bowsmail3r", new Callback<IEmail>() {
            @Override
            public void onSuccess(IEmail object) {
                //Called if successful
                assertTrue(true);
            }

            @Override
            public void onFailure(String msg) {
                //called if failure
                assertFalse(true);
            }
        });
    }
}