package edu.chl.mailbowser.address.models;

import edu.chl.mailbowser.address.models.Address;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by OscarEvertsson on 30/04/15.
 */
public class AddressTest {
    private Address address;
    private final String s1 = "oscar.evertsson@live.com";

    @Before
    public void intitialize() {
        address = new Address(s1);
    }

    @Test
    public void testGetJavaxAddress() throws Exception {
        assertTrue(address.getJavaxAddress().getAddress().equals(s1));
    }

    @Test
    public void testGetString() throws Exception {
        assertTrue(address.getString().equals(s1));
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(address.equals(address));
        assertFalse(address.equals(null));
        assertFalse(address.equals(new Object()));
        assertFalse(address.equals(10));
        assertTrue(address.equals(new Address(s1)));
    }

    @Test
    public void testHashCode() throws Exception {
        assertTrue(address.hashCode() == address.hashCode());
        assertFalse(address.hashCode() == s1.hashCode());
    }
}