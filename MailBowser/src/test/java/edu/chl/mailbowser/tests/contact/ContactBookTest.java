package edu.chl.mailbowser.tests.contact;

import edu.chl.mailbowser.contact.Contact;
import edu.chl.mailbowser.contact.ContactBook;
import edu.chl.mailbowser.contact.IContact;
import edu.chl.mailbowser.contact.IContactBook;
import edu.chl.mailbowser.tests.mock.MockContact;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class ContactBookTest {
    @Test
    public void testAddAndRemoveContact() throws Exception {
        // test if contact book is initiated with zero contacts
        ContactBook c1 = new ContactBook();
        assertEquals(c1.getContacts().size(), 0);

        // test if contacts are added correctly
        ContactBook c2 = new ContactBook();
        c2.addContact(new MockContact());
        assertEquals(c2.getContacts().size(), 1);
        c2.addContact(new MockContact());
        assertEquals(c2.getContacts().size(), 2);

        // test if null contacts are added
        ContactBook c3 = new ContactBook();
        c3.addContact(null);
        assertEquals(c3.getContacts().size(), 0);

        // test if removing non-added contacts does anything
        ContactBook c4 = new ContactBook();
        c4.removeContact(new MockContact());
        assertEquals(c4.getContacts().size(), 0);

        // test if duplicate contacts are added and removed correctly
        ContactBook c5 = new ContactBook();
        MockContact mc1 = new MockContact();
        c5.addContact(mc1);
        assertEquals(c5.getContacts().size(), 1);
        c5.addContact(mc1);
        assertEquals(c5.getContacts().size(), 1);
        c5.removeContact(mc1);
        assertEquals(c5.getContacts().size(), 0);
        c5.removeContact(mc1);
        assertEquals(c5.getContacts().size(), 0);

        // test if removing nulls are handled correctly
        ContactBook c6 = new ContactBook();
        c6.removeContact(null);
        assertEquals(c6.getContacts().size(), 0);
    }

    @Test
    public void testGetMatchingContacts() throws Exception {
        // MockContacts matches only if the query is an empty string
        ContactBook c1 = new ContactBook();
        c1.addContact(new MockContact());
        c1.addContact(new MockContact());
        c1.addContact(new MockContact());
        assertEquals(c1.getMatchingContacts("").size(), 3);

        ContactBook c2 = new ContactBook();
        c2.addContact(new MockContact());
        c2.addContact(new MockContact());
        c2.addContact(new MockContact());
        assertEquals(c2.getMatchingContacts("123").size(), 0);
    }

    //Since the read method is dependent of that it is possibly to read
    //and that a file exists this is not an actual unit test but more
    //like a integration test. We will also assume that all other method
    //works and that contact is functional.
    @Test
    public void testReadWriteContacts() throws Exception {
        IContactBook contactBook = new ContactBook();
        IContact contact1 = new Contact("Jepser","Jaxing");
        IContact contact2 = new Contact("Mats", "Hogberg");
        IContact contact3 = new Contact("Oscar", "Evertsson");
        IContact contact4 = new Contact("Filip", "Hallqvist");
        contactBook.addContact(contact1);
        contactBook.addContact(contact2);
        contactBook.addContact(contact3);
        contactBook.addContact(contact4);
        contactBook.writeContacts("IntegrationTesting.ser");
        contactBook.readContacts("IntegrationTesting.ser");
        assertTrue(contactBook.getContacts().contains(contact1));
        assertTrue(contactBook.getContacts().contains(contact2));
        assertTrue(contactBook.getContacts().contains(contact3));
        assertTrue(contactBook.getContacts().contains(contact4));

        // test what happens when a non-existing file is read
        ContactBook contactBook1 = new ContactBook();
        boolean result = contactBook1.readContacts("fileThatDoesntExist");
        assertFalse(result);
    }
}