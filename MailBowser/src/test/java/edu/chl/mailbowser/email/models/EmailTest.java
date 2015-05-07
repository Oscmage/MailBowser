package edu.chl.mailbowser.email.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
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