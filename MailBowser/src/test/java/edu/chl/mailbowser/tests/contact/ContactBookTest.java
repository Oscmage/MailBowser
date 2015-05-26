package edu.chl.mailbowser.tests.contact;

import edu.chl.mailbowser.contact.Contact;
import edu.chl.mailbowser.contact.ContactBook;
import edu.chl.mailbowser.contact.IContact;
import edu.chl.mailbowser.contact.IContactBook;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class ContactBookTest {


    @Test
    public void testAddAndRemoveContact() throws Exception {
        IContactBook contactBook = new ContactBook();
        IContact contact = new Contact(" ", " ");
        contactBook.addContact(contact);
        assertTrue(contactBook.getContacts().size() == 1);
        contactBook.removeContact(contact);
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
    }
}