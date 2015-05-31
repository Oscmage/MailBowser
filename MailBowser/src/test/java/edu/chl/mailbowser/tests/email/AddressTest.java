package edu.chl.mailbowser.tests.email;

import edu.chl.mailbowser.email.Address;
import org.junit.Before;
import org.junit.Test;

import javax.mail.internet.InternetAddress;

import static org.junit.Assert.*;

/**
 * Created by OscarEvertsson on 30/04/15.
 */
public class AddressTest {
    private Address address;
    private final String s1 = "oscar.evertsson@live.com";

    @Before
    public void initialize() {
        address = new Address(s1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorException(){
        new Address("hoinksdaoinsd@@@@@....com");
    }

    @Test
    public void testIsValidAddress(){
        //Test with a correct mail
        assertTrue(Address.isValidAddress(s1));

        //Several tests with incorrect strings.
        assertFalse(Address.isValidAddress(""));
        assertFalse(Address.isValidAddress("asdqiwoenioqnw"));
        assertFalse(Address.isValidAddress("asdhotmail.com"));
        assertFalse(Address.isValidAddress("@."));
    }

    @Test
    public void testGetJavaxAddress() throws Exception {
        //Checks whether the JavaMailAddress got the same after being created
        assertTrue(address.getJavaMailAddress().getAddress().equals(s1));

        //Checks if getJavaMailAddress returns the same as creating a new InternetAddress on your own
        assertEquals(address.getJavaMailAddress(), new InternetAddress(s1));
    }

    @Test
    public void testGetString() throws Exception {
        //Checks if the same string is returned after creation
        assertTrue(address.getString().equals(s1));
    }

    @Test
    public void testEquals() throws Exception {
        //Same object test
        assertTrue(address.equals(address));

        //Null test
        assertFalse(address.equals(null));

        //new object test
        assertFalse(address.equals(new Object()));

        //integer test
        assertFalse(address.equals(10));

        //new address object with same string
        assertTrue(address.equals(new Address(s1)));

        //Address equals it's string
        assertFalse(address.equals(s1));
    }

    @Test
    public void testHashCode() throws Exception {
        //Same value always?
        assertTrue(address.hashCode() == address.hashCode());

        //The address object vs it's string
        assertFalse(address.hashCode() == s1.hashCode());
    }

    @Test
    public void testMatches() throws Exception {
        //First 3 letter same as given string on creation
        assertTrue(address.matches("osc"));

        //Whole address same
        assertTrue(address.matches("oscar.evertsson@live.com"));

        //Some of the address
        assertTrue(address.matches("evert"));

        //Some text that's not in address
        assertFalse(address.matches("lol"));

        //Null test
        assertFalse(address.matches(null));

        //Test with empty string
        assertTrue(address.matches(""));

        //Two parts of the address combined
        assertFalse(address.matches("oscliv"));

        //Two other parts combined
        assertFalse(address.matches("osc@live.com"));
    }
}