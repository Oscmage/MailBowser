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

    //private String address;

    public MockAddress() {}

    //public MockAddress(String address) {
    //    this.address = address;
    //}

    @Override
    public Address getJavaMailAddress() {
        getJavaMailAddressCalled = true;
        try {
            return new InternetAddress("test@test.com");
        } catch (AddressException e) {
            e.printStackTrace();
            return null;
        }
        //try {
        //    return new InternetAddress(address);
        //} catch (AddressException e) {
        //    e.printStackTrace();
        //    return null;
        //}
    }

    @Override
    public String getString() {
        return null;
        //return address;
    }

    @Override
    public boolean matches(String query) {
        matchesCalled = true;
        return false;
    }

    //@Override
    //public boolean equals(Object obj) {
    //    if (obj == this) {
    //        return true;
    //    } else if (obj == null) {
    //        return false;
    //    } else if (!(obj instanceof IAddress)) {
    //        return false;
    //    }
    //
    //    IAddress address = (IAddress) obj;
    //    return address.getString().equals(this.address);
    //}
}
