package edu.chl.mailbowser.email.models;

import org.junit.Before;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by OscarEvertsson on 30/04/15.
 */
public class EmailTest {
    private Email email;

    @Before
    public void setUp() {
        List<IAddress> recipients = new ArrayList<>();
        recipients.add(new Address("address@hotmail.com"));
        email = new Email("Subject", "Content", new Address("mailbows3r@gmail.com"), recipients);
    }

    @Test
    public void testGetJavaxMessage() throws Exception {
        Session s = Session.getInstance(new Properties());
        Message m = email.getJavaxMessage(s);

        assertEquals(m.getSubject(), "Subject");
        assertEquals(m.getContent(), "Content");
        assertEquals(m.getFrom().length, 1);
        assertEquals(m.getFrom()[0], new InternetAddress("mailbows3r@gmail.com"));
        assertEquals(m.getRecipients(Message.RecipientType.TO).length, 1);
        assertEquals(m.getRecipients(Message.RecipientType.TO)[0], new InternetAddress("address@hotmail.com"));
    }

    @Test
    public void testMatches() throws Exception {
        assertTrue(email.matches("subject"));
        assertTrue(email.matches("SUBJECT"));
        assertTrue(email.matches("content"));
        assertTrue(email.matches("CONTENT"));
        assertTrue(email.matches("mailbows3r@gmail.com"));
        assertTrue(email.matches("MAILBOWS3R@GMAIL.COM"));
        assertTrue(email.matches("address@hotmail.com"));
        assertTrue(email.matches("ADDRESS@HOTMAIL.COM"));
        assertTrue(email.matches("ubje"));
        assertTrue(email.matches("nten"));
        assertTrue(email.matches("bows"));
        assertTrue(email.matches("hotm"));
        assertFalse(email.matches("abcdefg"));
        assertFalse(email.matches("ABCDEFG"));
        assertFalse(email.matches(null));
        assertTrue(email.matches(""));
    }
}