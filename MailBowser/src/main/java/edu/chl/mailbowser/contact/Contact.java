package edu.chl.mailbowser.contact;

import edu.chl.mailbowser.email.IAddress;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by jesper on 2015-05-20.
 *
 * A concrete implementation of the IContact interface. Objects of this class are immutable.
 */
public class Contact implements IContact {
    private static final long serialVersionUID = -2918882420849049598L;

    // each contact object gets a unique id
    private UUID uuid = UUID.randomUUID();
    private Date createdDate = new Date();

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
        this("", "");
    }

    /**
     * Creates a new contact with a list of addresses. Only non-null addresses are added to the contact.
     *
     * @param firstName Contacts first name
     * @param lastName Contacts last name
     * @param addresses Contacts addresses
     * @throws IllegalArgumentException if firstName or lastName is null
     */
    public Contact (String firstName, String lastName, List<? extends IAddress> addresses){
        this(firstName, lastName);

        if (addresses != null) {
            for (IAddress address : addresses) {
                if (address != null) {
                    this.emailAddresses.add(address);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFullName() {
        if (lastName.isEmpty() && firstName.isEmpty()) {
            return "";
        } else if (lastName.isEmpty()) {
            return firstName;
        } else if (firstName.isEmpty()) {
            return lastName;
        }

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
     */
    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Date getCreatedDate() {
        return (Date) createdDate.clone();
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public List<IAddress> getEmailAddresses() {
        return new ArrayList<>(emailAddresses);
    }


    /**
     * Compares an IContact to this contact. Comparison is first done by comparing the contacts full names. If their
     * names are equal, their created dates are compared. If they too are equal, their UUID:s are compared.
     *
     * @param contact the contact to compare this contact with
     * @return {@inheritDoc}
     */
    @Override
    public int compareTo(IContact contact) {
        int fullNameComparison = this.getFullName().toLowerCase().compareTo(contact.getFullName().toLowerCase());

        if (fullNameComparison != 0) {
            return fullNameComparison;
        }

        int addedDateComparison = this.createdDate.compareTo(contact.getCreatedDate());

        if (addedDateComparison != 0) {
            return -addedDateComparison;
        }

        return uuid.compareTo(contact.getUUID());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean matches(String query) {
        return query != null && getFullName().contains(query);
    }

    /**
     * Determines if this contact is equal to an object.
     *
     * @param o the object to compare this contact with
     * @return true if the object is a contact and has the same UUID as this contact.
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
        return this.uuid.equals(c.getUUID());
    }

    @Override
    public int hashCode(){
        return uuid.hashCode() * 7;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
