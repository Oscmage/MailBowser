package edu.chl.mailbowser.tests.account.models;

import edu.chl.mailbowser.email.models.IAddress;

import javax.mail.Address;

/**
 * Created by jesper on 2015-05-12.
 */
public class MockAddress implements IAddress {
    @Override
    public Address getJavaxAddress() {
        return null;
    }

    @Override
    public String getString() {
        return null;
    }

    @Override
    public boolean matches(String query) {
        return false;
    }
}
