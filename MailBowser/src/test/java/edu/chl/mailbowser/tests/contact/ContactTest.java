package edu.chl.mailbowser.tests.contact;

import edu.chl.mailbowser.contact.Contact;
import edu.chl.mailbowser.contact.IContact;
import edu.chl.mailbowser.tests.mock.MockAddress;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public void testConstructors() throws Exception {
        // create an empty contact and test if its names are empty. also test if created date and uuid are set
        // correctly
        Contact c1 = new Contact();
        assertEquals(c1.getFirstName(), "");
        assertEquals(c1.getLastName(), "");
        assertTrue(Math.abs(c1.getCreatedDate().getTime() - (new Date()).getTime()) < 100);
        assertNotEquals(c1.getUUID(), null);

        // test if names are set correctly. also test if created date and uuid are set correctly
        Contact c2 = new Contact("abc", "123");
        assertEquals(c2.getFirstName(), "abc");
        assertEquals(c2.getLastName(), "123");
        assertTrue(Math.abs(c2.getCreatedDate().getTime() - (new Date()).getTime()) < 100);
        assertNotEquals(c2.getUUID(), null);

        // test if a contact with null firstname and/or lastname can be created
        boolean exceptionThrown = false;
        try {
            new Contact(null, "");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        exceptionThrown = false;
        try {
            new Contact("", null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        exceptionThrown = false;
        try {
            new Contact(null, null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        // tests if addresses are set correcty by constructor. also test if created date and uuid are set correctly
        List<MockAddress> a1 = new ArrayList<>();
        a1.add(new MockAddress());
        a1.add(new MockAddress());
        a1.add(new MockAddress());

        Contact c3 = new Contact("abc", "123", a1);
        assertEquals(c3.getFirstName(), "abc");
        assertEquals(c3.getLastName(), "123");
        assertEquals(c3.getEmailAddresses().size(), 3);
        assertTrue(Math.abs(c3.getCreatedDate().getTime() - (new Date()).getTime()) < 100);
        assertNotEquals(c3.getUUID(), null);

        // test if null addresses are added
        List<MockAddress> a2 = new ArrayList<>();
        a2.add(new MockAddress());
        a2.add(null);
        a2.add(new MockAddress());

        Contact c4 = new Contact("", "", a2);
        assertEquals(c4.getEmailAddresses().size(), 2);

        // test if an empty list is allowed
        Contact c5 = new Contact("", "", new ArrayList<>());
        assertEquals(c5.getEmailAddresses().size(), 0);

        // test if you can create a contact with null firstname, lastname and/or addresses
        exceptionThrown = false;
        try {
            new Contact(null, "", new ArrayList<>());
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        exceptionThrown = false;
        try {
            new Contact("", null, new ArrayList<>());
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        exceptionThrown = false;
        Contact c6 = null;
        try {
            c6 = new Contact("", "", null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertFalse(exceptionThrown);
        assertNotEquals(c6, null);
        assertEquals(c6.getEmailAddresses().size(), 0);

        // test if two contacts uuid are the same
        Contact c7 = new Contact();
        Contact c8 = new Contact();
        assertNotEquals(c7.getUUID(), c8.getUUID());
    }

    @Test
    public void testGetFullName() throws Exception {
        // test if full name works correctly
        Contact c1 = new Contact("abc", "123");
        assertEquals(c1.getFullName(), "123, abc");

        // test if full name is an empty string if contacts names are empty
        Contact c2 = new Contact("", "");
        assertEquals(c2.getFullName(), "");

        // test if full name is correct if one name isn't supplied
        Contact c3 = new Contact("abc", "");
        assertEquals(c3.getFullName(), "abc");

        Contact c4 = new Contact("", "123");
        assertEquals(c4.getFullName(), "123");
    }

    @Test
    public void testCompareTo() throws Exception {
        // tests if contacts are sorted correctly. a timeout is put between constructors to get different created times
        Contact c1 = new Contact();
        TimeUnit.SECONDS.sleep(1);
        Contact c2 = new Contact("abc", "123");
        TimeUnit.SECONDS.sleep(1);
        Contact c3 = new Contact("abc", "123");

        assertTrue(c1.compareTo(c1) == 0);
        assertTrue(c1.compareTo(c2) < 0);
        assertTrue(c2.compareTo(c1) > 0);
        assertTrue(c2.compareTo(c2) == 0);
        assertTrue(c2.compareTo(c3) > 0);
        assertTrue(c3.compareTo(c2) < 0);

        // tests if a NullPointerException is thrown when comparing to null
        boolean exceptionThrown = false;
        try {
            c1.compareTo(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

    }

    @Test
    public void testMatches() throws Exception {
        Contact c1 = new Contact("abc", "123");

        assertTrue(c1.matches(""));
        assertFalse(c1.matches(null));
        assertTrue(c1.matches("abc"));
        assertTrue(c1.matches("123"));
        assertTrue(c1.matches("b"));
        assertTrue(c1.matches("2"));
        assertFalse(c1.matches("def"));
        assertFalse(c1.matches("456"));
        assertFalse(c1.matches("abd"));
        assertFalse(c1.matches("124"));
    }

    @Test
    public void testEquals() throws Exception {
        // tests if two identical contacts are equal. they shouldn't be, since they have different UUID:s
        Contact c1 = new Contact();
        Contact c2 = new Contact();

        assertTrue(c1.equals(c1));
        assertFalse(c2.equals(c1));
        assertFalse(c1.equals(c2));
        assertTrue(c2.equals(c2));
    }

    @Test
    public void testHashCode() throws Exception {
        // tests if two identical contacts have the same hash code. they shouldn't have, since they have different UUID:s
        Contact c1 = new Contact();
        Contact c2 = new Contact();
        assertEquals(c1.hashCode(), c1.hashCode());
        assertNotEquals(c1.hashCode(), c2.hashCode());
        assertEquals(c2.hashCode(), c2.hashCode());
    }
}