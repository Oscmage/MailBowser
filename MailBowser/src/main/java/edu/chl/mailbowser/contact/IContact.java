package edu.chl.mailbowser.contact;

import edu.chl.mailbowser.email.IAddress;
import edu.chl.mailbowser.utils.search.Searchable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jesper on 2015-05-20.
 * This interface represent a contact with first name, last name, full name and email address(es).
 */
public interface IContact extends Comparable<IContact>, Searchable, Serializable {
    /**
     * Returns the full name as a string in the format "first name + ; + last name".
     * @return
     */
    String getFullName();

    /**
     * Returns the first name.
     * @return
     */
    String getFirstName();

    /**
     * Returns the last name.
     * @return
     */
    String getLastName();

    /**
     * Sets the first name to the specified string.
     * @param firstName
     */
    void setFirstName(String firstName);

    /**
     * Sets the last name to the specified string.
     * @param lastName
     */
    void setLastName(String lastName);

    /**
     * Adds the email address.
     * @param address
     */
    void addAddress(IAddress address);

    /**
     * Adds all addresses for the specified list to the contact.
     * @param addresses
     */
    void addAllAddresses(List<IAddress> addresses);

    /**
     * Clears the list of addresses completely.
     */
    void removeAllAddresses();

    /**
     * Returns a list of all email-addresses.
     * @return
     * */
    List<IAddress> getEmailAddresses();
}
