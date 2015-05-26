package edu.chl.mailbowser.contact;

import java.util.Set;

/**
 * Created by OscarEvertsson on 20/05/15.
 * This is an interface to represent the contact book.
 */
public interface IContactBook {

    /**
     * Adds a contact.
     */
    void addContact(IContact contact);

    /**
     * Removes the specified contact.
     */
    void removeContact(IContact contact);

    /**
     * Returns a set with all contacts
     * @return
     */
    Set<IContact> getContacts();

    /**
     * Returns a set of contacts that matches the given string.
     * @param query
     * @return
     */
    Set<IContact> getMatchingContacts(String query);

    /**
     * Reads a file from disk with IContacts and adds them to this contact book.
     *
     * @param filename the file to look for contacts in
     * @return false if no contacts are found, otherwise true
     */
    boolean readContacts(String filename);

    /**
     * Writes all contacts that have been added to this contact handler to a file on disk.
     *
     * @param filename the file to write the contacts to
     * @return true if the write was successful, otherwise false
     */
    boolean writeContacts(String filename);
}
