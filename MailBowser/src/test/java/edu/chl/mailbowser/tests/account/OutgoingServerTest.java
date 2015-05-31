package edu.chl.mailbowser.tests.account;

import edu.chl.mailbowser.account.IOutgoingServer;
import edu.chl.mailbowser.account.OutgoingServer;
import edu.chl.mailbowser.email.Address;
import edu.chl.mailbowser.email.Email;
import edu.chl.mailbowser.email.IAddress;
import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.event.IObserver;
import edu.chl.mailbowser.tests.mock.MockEmail;
import edu.chl.mailbowser.utils.Callback;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        List<IAddress> to = new ArrayList<>();
        to.add(new Address("mailbowser@teleworm.us"));
        Email email = new Email.Builder("Subject", "Content").sender(new Address("mailbows3r@gmail.com")).to(to).build();
        outgoingServer1.send(email, "mailbows3r@gmail.com", "bowsmail3r", new Callback<IEmail>() {
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