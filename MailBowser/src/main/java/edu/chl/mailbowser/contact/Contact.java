package edu.chl.mailbowser.contact;

import edu.chl.mailbowser.email.models.IAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jesper on 2015-05-20.
 */
public class Contact implements IContact {
    private String firstName;
    private String lastName;
    private List<IAddress> emailAddresses;

    @Override
    /**
     * @return the full name with the last name first and separated by semi-colon
     */
    public String getFullName() {
        return lastName+";"+firstName;
    }

    @Override
    /**
     * @return the last name of the contact
     */
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public List<IAddress> getEmailAddresses() {
        return new ArrayList<IAddress>(emailAddresses);
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof String) {
            String s = (String)o;
            return getFullName().compareTo(s);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean matches(String query) {
        return getFullName().matches(query);
    }
}
