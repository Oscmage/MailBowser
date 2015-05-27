package edu.chl.mailbowser.contact;

import edu.chl.mailbowser.email.IAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jesper on 2015-05-20.
 */
public class Contact implements IContact {
    private String firstName;
    private String lastName;
    private List<IAddress> emailAddresses = new ArrayList<>();

    /**
     * Creates a new contact with no emailAddresses
     * @param firstName Contacts first name
     * @param lastName Contacts last name
     * @throws IllegalArgumentException if firstName or lastName is null
     */
    public Contact (String firstName, String lastName){
        if (firstName == null) {
            throw new IllegalArgumentException("Can't create a contact with first name null");
        }
        if (lastName == null) {
            throw new IllegalArgumentException("Can't create a contact with last name null");
        }

        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Creates an empty Contact with first name and last name set to empty strings.
     */
    public Contact (){
        this.firstName = "";
        this.lastName = "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFullName() {
        return lastName + ", " + firstName;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getFirstName() {
        return firstName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLastName() {
        return lastName;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if firstName is null
     */
    @Override
    public void setFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException("Can't set first name to null");
        }

        this.firstName = firstName;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if lastName is null
     */
    @Override
    public void setLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Can't set last name to null");
        }

        this.lastName = lastName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAddress(IAddress address) {
        if (address != null) {
            emailAddresses.add(address);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAllAddresses(List<IAddress> addresses) {
        if (addresses != null) {
            for (IAddress address : addresses) {
                if (address != null) {
                    emailAddresses.add(address);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public List<IAddress> getEmailAddresses() {
        return new ArrayList<>(emailAddresses);
    }

    @Override
    public int compareTo(IContact contact) {
            return getFullName().compareTo(contact.getFullName()            );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean matches(String query) {
        if(query != null)
            return getFullName().contains(query);
        return false;
    }

    /**
     * Returns true if the object has the same last name, first name and email-addresses.
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode(){
        return (firstName.hashCode() * 7)  + (lastName.hashCode() * 17 )+ (emailAddresses.hashCode() * 23 );
    }
}
