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

    /**
     * Creates a new contact with no emailAddresses
     * @param firstName Contacts first name
     * @param lastName Contacts last name
     */
    public Contact (String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

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
    public int compareTo(IContact contact) {
            return getFullName().compareTo(contact.getFullName()            );
    }

    @Override
    public boolean matches(String query) {
        return getFullName().matches(query);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        } else if (!o.getClass().equals(this.getClass())) {
            return false;
        }
        Contact c = (Contact) o;
        if (!this.firstName.equals(c.firstName)) {
            return false;
        } else if (!this.lastName.equals(c.getLastName())) {
            return false;
        } else if (!this.getEmailAddresses().equals(c.getEmailAddresses())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        return (firstName.hashCode() * 7)  + (lastName.hashCode() * 17 )+ (emailAddresses.hashCode() * 23 );
    }
}
