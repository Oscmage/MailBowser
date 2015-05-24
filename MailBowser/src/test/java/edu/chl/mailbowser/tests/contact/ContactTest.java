package edu.chl.mailbowser.tests.contact;

import edu.chl.mailbowser.contact.Contact;
import edu.chl.mailbowser.contact.IContact;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContactTest {

    IContact contactA;
    IContact contactB;
    IContact contactC;

    @Before
    public void setUp() throws Exception {
        contactA = new Contact("Jesper","Jaxing");
        contactB = new Contact("Jaxing","Jesper");
        contactC = new Contact("Jesper","Jaxing");
    }

    @Test
    public void testCompareTo() throws Exception {
        assertTrue(contactA.compareTo(contactA)==0);
        assertTrue(contactA.compareTo(contactC)==0);
        assertTrue(contactA.compareTo(contactB)<0);
        assertTrue(contactB.compareTo(contactC)>0);

    }

    @Test
    public void testMatches() throws Exception {
        assertTrue(contactA.matches("Jaxing"));
        assertFalse(contactA.matches("N"));
        assertTrue(contactA.matches(contactC.getFullName()));
        assertFalse(contactA.matches(null));
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(contactA.equals(contactA));
        assertTrue(contactA.equals(contactC));
        assertFalse(contactA.equals(contactB));
    }

    @Test
    public void testHashCode() throws Exception {

    }

    @Test
    public void testHashAndEquals() throws Exception {

    }
}