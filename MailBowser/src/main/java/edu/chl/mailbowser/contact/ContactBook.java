package edu.chl.mailbowser.contact;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by OscarEvertsson on 20/05/15.
 */
public class ContactBook implements IContactBook{

    private SortedSet<IContact> contacts = new TreeSet<IContact>();

    @Override
    public void addContact(IContact contact) {
        this.contacts.add(contact);
    }

    @Override
    public void removeContact(IContact contact) {
        this.contacts.remove(contact);
    }

    @Override
    public SortedSet<IContact> getContacts() {
        return this.contacts;
    }

    @Override
    public SortedSet<IContact> getMatchingContacts(String query) {
        return null;
    }
}
