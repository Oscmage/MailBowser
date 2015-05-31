package edu.chl.mailbowser.tests.contact;

import edu.chl.mailbowser.contact.Contact;
import edu.chl.mailbowser.contact.ContactBook;
import edu.chl.mailbowser.contact.IContact;
import edu.chl.mailbowser.contact.IContactBook;
import edu.chl.mailbowser.tests.mock.MockContact;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContactBookTest {

    private IContactBook cb1;
    private IContactBook cb2;


    @Before
    public void init(){
        cb1 = new ContactBook();
        cb2 = new ContactBook();
    }

    @Test
    public void testAddAndRemoveContact() throws Exception {
        // test if contact book is initiated with zero contacts
        assertEquals(cb1.getContacts().size(), 0);

        // test if contacts are added correctly
        cb2.addContact(new MockContact());
        assertEquals(cb2.getContacts().size(), 1);
        cb2.addContact(new MockContact());
        assertEquals(cb2.getContacts().size(), 2);

        // test if null contacts are added
        cb1 = new ContactBook();
        cb1.addContact(null);
        assertEquals(cb1.getContacts().size(), 0);

        // test if removing non-added contacts does anything
        cb2 = new ContactBook();
        cb2.removeContact(new MockContact());
        assertEquals(cb2.getContacts().size(), 0);

        // test if duplicate contacts are added and removed correctly
        cb1 = new ContactBook();
        MockContact mc1 = new MockContact();
        cb1.addContact(mc1);
        assertEquals(cb1.getContacts().size(), 1);
        cb1.addContact(mc1);
        assertEquals(cb1.getContacts().size(), 1);
        cb1.removeContact(mc1);
        assertEquals(cb1.getContacts().size(), 0);
        cb1.removeContact(mc1);
        assertEquals(cb1.getContacts().size(), 0);

        // test if removing nulls are handled correctly
        cb2 = new ContactBook();
        cb2.removeContact(null);
        assertEquals(cb2.getContacts().size(), 0);
    }

    @Test
    public void testGetMatchingContacts() throws Exception {
        // MockContacts matches only if the query is an empty string
        cb1.addContact(new MockContact());
        cb1.addContact(new MockContact());
        cb1.addContact(new MockContact());
        assertEquals(cb1.getMatchingContacts("").size(), 3);

        cb2.addContact(new MockContact());
        cb2.addContact(new MockContact());
        cb2.addContact(new MockContact());
        assertEquals(cb2.getMatchingContacts("123").size(), 0);
    }

    //Since the read method is dependent of that it is possibly to read
    //and that a file exists this is not an actual unit test but more
    //like a integration test. We will also assume that all other method
    //works and that contact is functional.
    @Test
    public void testReadWriteContacts() throws Exception {
        IContact contact1 = new Contact("Jepser","Jaxing");
        IContact contact2 = new Contact("Mats", "Hogberg");
        IContact contact3 = new Contact("Oscar", "Evertsson");
        IContact contact4 = new Contact("Filip", "Hallqvist");
        cb1.addContact(contact1);
        cb1.addContact(contact2);
        cb1.addContact(contact3);
        cb1.addContact(contact4);
        cb1.writeContacts("IntegrationTesting.ser");
        cb1.readContacts("IntegrationTesting.ser");
        assertTrue(cb1.getContacts().contains(contact1));
        assertTrue(cb1.getContacts().contains(contact2));
        assertTrue(cb1.getContacts().contains(contact3));
        assertTrue(cb1.getContacts().contains(contact4));

        // test what happens when a non-existing file is read
        ContactBook contactBook1 = new ContactBook();
        boolean result = contactBook1.readContacts("fileThatDoesntExist");
        assertFalse(result);
    }

    @Test
    public void testEquals(){
        //Same object
        assertTrue(cb1.equals(cb1));
        //Different class
        assertFalse(cb1.equals("Oscar Evertsson"));
        //Null test
        assertFalse(cb1.equals(null));
        //Different objects with same contact
        IContact contact = new MockContact();
        cb1.addContact(contact);
        cb2.addContact(contact);
        assertTrue(cb1.equals(cb2));
        //Same class but not same contacts
        cb1 = new ContactBook();
        assertFalse(cb1.equals(cb2));
    }

    @Test
    public void testHashCode(){
        //Returns same hashCode when called twice?
        assertTrue(cb1.hashCode() == cb1.hashCode());
        //Different objects but same contacts
        assertTrue(cb1.hashCode() == cb2.hashCode());
        //Different contacts
        cb2.addContact(new MockContact());
        assertFalse(cb1.hashCode() == cb2.hashCode());
    }
}