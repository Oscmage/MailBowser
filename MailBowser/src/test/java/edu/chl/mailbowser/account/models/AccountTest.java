package edu.chl.mailbowser.account.models;

import edu.chl.mailbowser.address.models.Address;
import edu.chl.mailbowser.mailserver.models.IncomingServer;
import edu.chl.mailbowser.mailserver.models.OutgoingServer;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class AccountTest {
    Account a = new Account(new Address("Jax@gmail.com"), "hej", new IncomingServer("@","@"), new OutgoingServer("@","@"));
    Account b = new Account(new Address("Jax@gmail.com"), "hej", new IncomingServer("@","@"), new OutgoingServer("@","@"));
    Account c = new Account(new Address("lol@lol.de"), "hej", new IncomingServer(" "," "), new OutgoingServer(" "," "));

    @Test
    public void testEquals() throws Exception {
        assertTrue(a.equals(a));
        assertFalse(a.equals(null));
        assertFalse(a.equals(new Object()));
        assertFalse(a.equals(c));
        assertTrue(a.equals(b));
    }

    @Test
    public void testHash() throws Exception {
        assertTrue(a.hashCode() == a.hashCode());
        assertTrue(a.hashCode() == b.hashCode());
        assertFalse(a.hashCode() == c.hashCode());
    }

    @Test
    public void testEqualsAndHash() throws Exception {
        assertTrue(!(a.equals(a)) || (a.hashCode() == a.hashCode()));
        assertFalse(!(a.equals(a))||(a.hashCode() == c.hashCode()));
    }
}