package edu.chl.mailbowser.tests.email;

import edu.chl.mailbowser.email.Address;
import edu.chl.mailbowser.email.Email;
import edu.chl.mailbowser.email.IAddress;
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
        Session s = Session.getInstance(new Properties()); //JavaMail class
        Message m = email.getJavaMailMessage(s); //JavaMail class

        //Subject is the same?
        assertEquals(m.getSubject(), "Subject");

        //Content is the same?
        assertEquals(m.getContent(), "Content");

        //From length equals 1?
        assertEquals(m.getFrom().length, 1);

        //Creates a new InternetAddress with the same string
        assertEquals(m.getFrom()[0], new InternetAddress("mailbows3r@gmail.com"));

        //recipients should be equals to 1
        assertEquals(m.getRecipients(Message.RecipientType.TO).length, 1);

        //Creates a InternetAddress with the same address
        assertEquals(m.getRecipients(Message.RecipientType.TO)[0], new InternetAddress("address@hotmail.com"));
    }

    @Test
    public void testMatches() throws Exception {

        //Same string but no caps
        assertTrue(email.matches("subject"));

        //Same string with all caps
        assertTrue(email.matches("SUBJECT"));

        //Same string but no caps
        assertTrue(email.matches("content"));

        //Same string but all caps
        assertTrue(email.matches("CONTENT"));

        //Same string
        assertTrue(email.matches("mailbows3r@gmail.com"));

        //Same string all caps
        assertTrue(email.matches("MAILBOWS3R@GMAIL.COM"));

        //Same string
        assertTrue(email.matches("address@hotmail.com"));

        //Same string all caps
        assertTrue(email.matches("ADDRESS@HOTMAIL.COM"));

        //Part of subject
        assertTrue(email.matches("ubje"));

        //Part of content
        assertTrue(email.matches("nten"));

        //Part of address
        assertTrue(email.matches("bows"));

        //Part of address
        assertTrue(email.matches("hotm"));

        //Random string
        assertFalse(email.matches("abcdefg"));

        //Random string caps
        assertFalse(email.matches("ABCDEFG"));

        //Null
        assertFalse(email.matches(null));

        //Empty string
        assertTrue(email.matches(""));
    }
}