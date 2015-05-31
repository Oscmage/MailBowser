package edu.chl.mailbowser.tests.mock;

import edu.chl.mailbowser.contact.IContact;
import edu.chl.mailbowser.email.IAddress;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by mats on 30/05/15.
 */
public class MockContact implements IContact {
    @Override
    public String getFullName() {
        return null;
    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public UUID getUUID() {
        return null;
    }

    @Override
    public Date getCreatedDate() {
        return null;
    }

    @Override
    public List<IAddress> getEmailAddresses() {
        return null;
    }

    @Override
    public int compareTo(IContact o) {
        if (this == o) {
            return 0;
        }
        return 1;
    }

    @Override
    public boolean matches(String query) {
        if (query.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }
}
