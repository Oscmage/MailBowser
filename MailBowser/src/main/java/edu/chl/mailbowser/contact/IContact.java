package edu.chl.mailbowser.contact;

import edu.chl.mailbowser.email.IAddress;
import edu.chl.mailbowser.utils.search.Searchable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by jesper on 2015-05-20.
 *
 * This interface represents a contact with first name, last name, full name and a list of email addresses.
 * Each contact should have a UUID for identifying it. This is because two contacts with the same name can be
 * two different contacts, and should not be equal.
 *
 * All implementations of this interface should be immutable.
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
     * Returns this contacts UUID.
     *
     * @return this contacts UUID
     */
    UUID getUUID();

    /**
     * Returns this contacts creation date.
     *
     * @return
     */
    Date getCreatedDate();

    /**
     * Returns a list of all email-addresses.
     * @return
     * */
    List<IAddress> getEmailAddresses();
}
