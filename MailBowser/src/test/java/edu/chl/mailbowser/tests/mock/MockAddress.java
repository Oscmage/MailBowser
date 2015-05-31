package edu.chl.mailbowser.tests.mock;

import edu.chl.mailbowser.email.IAddress;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by jesper on 2015-05-12.
 */
public class MockAddress implements IAddress {
    public boolean matchesCalled = false;
    public boolean getJavaMailAddressCalled = false;

    public MockAddress() {}

    @Override
    public Address getJavaMailAddress() {
        getJavaMailAddressCalled = true;
        try {
            return new InternetAddress("test@test.com");
        } catch (AddressException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getString() {
        return null;
    }

    @Override
    public boolean matches(String query) {
        matchesCalled = true;
        return false;
    }

    @Override
    public int compareTo(IAddress o) {
        return 0;
    }
}
